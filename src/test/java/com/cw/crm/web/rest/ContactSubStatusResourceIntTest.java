package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ContactSubStatus;
import com.cw.crm.domain.ContactStatus;
import com.cw.crm.repository.ContactSubStatusRepository;
import com.cw.crm.service.ContactSubStatusService;
import com.cw.crm.service.dto.ContactSubStatusDTO;
import com.cw.crm.service.mapper.ContactSubStatusMapper;
import com.cw.crm.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cw.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContactSubStatusResource REST controller.
 *
 * @see ContactSubStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ContactSubStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactSubStatusRepository contactSubStatusRepository;

    @Autowired
    private ContactSubStatusMapper contactSubStatusMapper;

    @Autowired
    private ContactSubStatusService contactSubStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restContactSubStatusMockMvc;

    private ContactSubStatus contactSubStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactSubStatusResource contactSubStatusResource = new ContactSubStatusResource(contactSubStatusService);
        this.restContactSubStatusMockMvc = MockMvcBuilders.standaloneSetup(contactSubStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactSubStatus createEntity(EntityManager em) {
        ContactSubStatus contactSubStatus = new ContactSubStatus()
            .name(DEFAULT_NAME);
        // Add required entity
        ContactStatus contactStatus = ContactStatusResourceIntTest.createEntity(em);
        em.persist(contactStatus);
        em.flush();
        contactSubStatus.setSubTypeOf(contactStatus);
        return contactSubStatus;
    }

    @Before
    public void initTest() {
        contactSubStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactSubStatus() throws Exception {
        int databaseSizeBeforeCreate = contactSubStatusRepository.findAll().size();

        // Create the ContactSubStatus
        ContactSubStatusDTO contactSubStatusDTO = contactSubStatusMapper.toDto(contactSubStatus);
        restContactSubStatusMockMvc.perform(post("/api/contact-sub-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactSubStatus in the database
        List<ContactSubStatus> contactSubStatusList = contactSubStatusRepository.findAll();
        assertThat(contactSubStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ContactSubStatus testContactSubStatus = contactSubStatusList.get(contactSubStatusList.size() - 1);
        assertThat(testContactSubStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContactSubStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactSubStatusRepository.findAll().size();

        // Create the ContactSubStatus with an existing ID
        contactSubStatus.setId(1L);
        ContactSubStatusDTO contactSubStatusDTO = contactSubStatusMapper.toDto(contactSubStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactSubStatusMockMvc.perform(post("/api/contact-sub-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactSubStatus in the database
        List<ContactSubStatus> contactSubStatusList = contactSubStatusRepository.findAll();
        assertThat(contactSubStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactSubStatusRepository.findAll().size();
        // set the field null
        contactSubStatus.setName(null);

        // Create the ContactSubStatus, which fails.
        ContactSubStatusDTO contactSubStatusDTO = contactSubStatusMapper.toDto(contactSubStatus);

        restContactSubStatusMockMvc.perform(post("/api/contact-sub-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ContactSubStatus> contactSubStatusList = contactSubStatusRepository.findAll();
        assertThat(contactSubStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactSubStatuses() throws Exception {
        // Initialize the database
        contactSubStatusRepository.saveAndFlush(contactSubStatus);

        // Get all the contactSubStatusList
        restContactSubStatusMockMvc.perform(get("/api/contact-sub-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactSubStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContactSubStatus() throws Exception {
        // Initialize the database
        contactSubStatusRepository.saveAndFlush(contactSubStatus);

        // Get the contactSubStatus
        restContactSubStatusMockMvc.perform(get("/api/contact-sub-statuses/{id}", contactSubStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactSubStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactSubStatus() throws Exception {
        // Get the contactSubStatus
        restContactSubStatusMockMvc.perform(get("/api/contact-sub-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactSubStatus() throws Exception {
        // Initialize the database
        contactSubStatusRepository.saveAndFlush(contactSubStatus);

        int databaseSizeBeforeUpdate = contactSubStatusRepository.findAll().size();

        // Update the contactSubStatus
        ContactSubStatus updatedContactSubStatus = contactSubStatusRepository.findById(contactSubStatus.getId()).get();
        // Disconnect from session so that the updates on updatedContactSubStatus are not directly saved in db
        em.detach(updatedContactSubStatus);
        updatedContactSubStatus
            .name(UPDATED_NAME);
        ContactSubStatusDTO contactSubStatusDTO = contactSubStatusMapper.toDto(updatedContactSubStatus);

        restContactSubStatusMockMvc.perform(put("/api/contact-sub-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubStatusDTO)))
            .andExpect(status().isOk());

        // Validate the ContactSubStatus in the database
        List<ContactSubStatus> contactSubStatusList = contactSubStatusRepository.findAll();
        assertThat(contactSubStatusList).hasSize(databaseSizeBeforeUpdate);
        ContactSubStatus testContactSubStatus = contactSubStatusList.get(contactSubStatusList.size() - 1);
        assertThat(testContactSubStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContactSubStatus() throws Exception {
        int databaseSizeBeforeUpdate = contactSubStatusRepository.findAll().size();

        // Create the ContactSubStatus
        ContactSubStatusDTO contactSubStatusDTO = contactSubStatusMapper.toDto(contactSubStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactSubStatusMockMvc.perform(put("/api/contact-sub-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactSubStatus in the database
        List<ContactSubStatus> contactSubStatusList = contactSubStatusRepository.findAll();
        assertThat(contactSubStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactSubStatus() throws Exception {
        // Initialize the database
        contactSubStatusRepository.saveAndFlush(contactSubStatus);

        int databaseSizeBeforeDelete = contactSubStatusRepository.findAll().size();

        // Get the contactSubStatus
        restContactSubStatusMockMvc.perform(delete("/api/contact-sub-statuses/{id}", contactSubStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactSubStatus> contactSubStatusList = contactSubStatusRepository.findAll();
        assertThat(contactSubStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSubStatus.class);
        ContactSubStatus contactSubStatus1 = new ContactSubStatus();
        contactSubStatus1.setId(1L);
        ContactSubStatus contactSubStatus2 = new ContactSubStatus();
        contactSubStatus2.setId(contactSubStatus1.getId());
        assertThat(contactSubStatus1).isEqualTo(contactSubStatus2);
        contactSubStatus2.setId(2L);
        assertThat(contactSubStatus1).isNotEqualTo(contactSubStatus2);
        contactSubStatus1.setId(null);
        assertThat(contactSubStatus1).isNotEqualTo(contactSubStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSubStatusDTO.class);
        ContactSubStatusDTO contactSubStatusDTO1 = new ContactSubStatusDTO();
        contactSubStatusDTO1.setId(1L);
        ContactSubStatusDTO contactSubStatusDTO2 = new ContactSubStatusDTO();
        assertThat(contactSubStatusDTO1).isNotEqualTo(contactSubStatusDTO2);
        contactSubStatusDTO2.setId(contactSubStatusDTO1.getId());
        assertThat(contactSubStatusDTO1).isEqualTo(contactSubStatusDTO2);
        contactSubStatusDTO2.setId(2L);
        assertThat(contactSubStatusDTO1).isNotEqualTo(contactSubStatusDTO2);
        contactSubStatusDTO1.setId(null);
        assertThat(contactSubStatusDTO1).isNotEqualTo(contactSubStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactSubStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactSubStatusMapper.fromId(null)).isNull();
    }
}
