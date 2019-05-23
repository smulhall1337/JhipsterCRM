package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ContactStatus;
import com.cw.crm.repository.ContactStatusRepository;
import com.cw.crm.service.ContactStatusService;
import com.cw.crm.service.dto.ContactStatusDTO;
import com.cw.crm.service.mapper.ContactStatusMapper;
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
 * Test class for the ContactStatusResource REST controller.
 *
 * @see ContactStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ContactStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactStatusRepository contactStatusRepository;

    @Autowired
    private ContactStatusMapper contactStatusMapper;

    @Autowired
    private ContactStatusService contactStatusService;

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

    private MockMvc restContactStatusMockMvc;

    private ContactStatus contactStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactStatusResource contactStatusResource = new ContactStatusResource(contactStatusService);
        this.restContactStatusMockMvc = MockMvcBuilders.standaloneSetup(contactStatusResource)
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
    public static ContactStatus createEntity(EntityManager em) {
        ContactStatus contactStatus = new ContactStatus()
            .name(DEFAULT_NAME);
        return contactStatus;
    }

    @Before
    public void initTest() {
        contactStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactStatus() throws Exception {
        int databaseSizeBeforeCreate = contactStatusRepository.findAll().size();

        // Create the ContactStatus
        ContactStatusDTO contactStatusDTO = contactStatusMapper.toDto(contactStatus);
        restContactStatusMockMvc.perform(post("/api/contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactStatus in the database
        List<ContactStatus> contactStatusList = contactStatusRepository.findAll();
        assertThat(contactStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ContactStatus testContactStatus = contactStatusList.get(contactStatusList.size() - 1);
        assertThat(testContactStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContactStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactStatusRepository.findAll().size();

        // Create the ContactStatus with an existing ID
        contactStatus.setId(1L);
        ContactStatusDTO contactStatusDTO = contactStatusMapper.toDto(contactStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactStatusMockMvc.perform(post("/api/contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactStatus in the database
        List<ContactStatus> contactStatusList = contactStatusRepository.findAll();
        assertThat(contactStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactStatusRepository.findAll().size();
        // set the field null
        contactStatus.setName(null);

        // Create the ContactStatus, which fails.
        ContactStatusDTO contactStatusDTO = contactStatusMapper.toDto(contactStatus);

        restContactStatusMockMvc.perform(post("/api/contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ContactStatus> contactStatusList = contactStatusRepository.findAll();
        assertThat(contactStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactStatuses() throws Exception {
        // Initialize the database
        contactStatusRepository.saveAndFlush(contactStatus);

        // Get all the contactStatusList
        restContactStatusMockMvc.perform(get("/api/contact-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContactStatus() throws Exception {
        // Initialize the database
        contactStatusRepository.saveAndFlush(contactStatus);

        // Get the contactStatus
        restContactStatusMockMvc.perform(get("/api/contact-statuses/{id}", contactStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactStatus() throws Exception {
        // Get the contactStatus
        restContactStatusMockMvc.perform(get("/api/contact-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactStatus() throws Exception {
        // Initialize the database
        contactStatusRepository.saveAndFlush(contactStatus);

        int databaseSizeBeforeUpdate = contactStatusRepository.findAll().size();

        // Update the contactStatus
        ContactStatus updatedContactStatus = contactStatusRepository.findById(contactStatus.getId()).get();
        // Disconnect from session so that the updates on updatedContactStatus are not directly saved in db
        em.detach(updatedContactStatus);
        updatedContactStatus
            .name(UPDATED_NAME);
        ContactStatusDTO contactStatusDTO = contactStatusMapper.toDto(updatedContactStatus);

        restContactStatusMockMvc.perform(put("/api/contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactStatusDTO)))
            .andExpect(status().isOk());

        // Validate the ContactStatus in the database
        List<ContactStatus> contactStatusList = contactStatusRepository.findAll();
        assertThat(contactStatusList).hasSize(databaseSizeBeforeUpdate);
        ContactStatus testContactStatus = contactStatusList.get(contactStatusList.size() - 1);
        assertThat(testContactStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContactStatus() throws Exception {
        int databaseSizeBeforeUpdate = contactStatusRepository.findAll().size();

        // Create the ContactStatus
        ContactStatusDTO contactStatusDTO = contactStatusMapper.toDto(contactStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactStatusMockMvc.perform(put("/api/contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactStatus in the database
        List<ContactStatus> contactStatusList = contactStatusRepository.findAll();
        assertThat(contactStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactStatus() throws Exception {
        // Initialize the database
        contactStatusRepository.saveAndFlush(contactStatus);

        int databaseSizeBeforeDelete = contactStatusRepository.findAll().size();

        // Get the contactStatus
        restContactStatusMockMvc.perform(delete("/api/contact-statuses/{id}", contactStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactStatus> contactStatusList = contactStatusRepository.findAll();
        assertThat(contactStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactStatus.class);
        ContactStatus contactStatus1 = new ContactStatus();
        contactStatus1.setId(1L);
        ContactStatus contactStatus2 = new ContactStatus();
        contactStatus2.setId(contactStatus1.getId());
        assertThat(contactStatus1).isEqualTo(contactStatus2);
        contactStatus2.setId(2L);
        assertThat(contactStatus1).isNotEqualTo(contactStatus2);
        contactStatus1.setId(null);
        assertThat(contactStatus1).isNotEqualTo(contactStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactStatusDTO.class);
        ContactStatusDTO contactStatusDTO1 = new ContactStatusDTO();
        contactStatusDTO1.setId(1L);
        ContactStatusDTO contactStatusDTO2 = new ContactStatusDTO();
        assertThat(contactStatusDTO1).isNotEqualTo(contactStatusDTO2);
        contactStatusDTO2.setId(contactStatusDTO1.getId());
        assertThat(contactStatusDTO1).isEqualTo(contactStatusDTO2);
        contactStatusDTO2.setId(2L);
        assertThat(contactStatusDTO1).isNotEqualTo(contactStatusDTO2);
        contactStatusDTO1.setId(null);
        assertThat(contactStatusDTO1).isNotEqualTo(contactStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactStatusMapper.fromId(null)).isNull();
    }
}
