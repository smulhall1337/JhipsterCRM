package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ContactSubType;
import com.cw.crm.repository.ContactSubTypeRepository;
import com.cw.crm.service.ContactSubTypeService;
import com.cw.crm.service.dto.ContactSubTypeDTO;
import com.cw.crm.service.mapper.ContactSubTypeMapper;
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
 * Test class for the ContactSubTypeResource REST controller.
 *
 * @see ContactSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ContactSubTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactSubTypeRepository contactSubTypeRepository;

    @Autowired
    private ContactSubTypeMapper contactSubTypeMapper;

    @Autowired
    private ContactSubTypeService contactSubTypeService;

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

    private MockMvc restContactSubTypeMockMvc;

    private ContactSubType contactSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactSubTypeResource contactSubTypeResource = new ContactSubTypeResource(contactSubTypeService);
        this.restContactSubTypeMockMvc = MockMvcBuilders.standaloneSetup(contactSubTypeResource)
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
    public static ContactSubType createEntity(EntityManager em) {
        ContactSubType contactSubType = new ContactSubType()
            .name(DEFAULT_NAME);
        return contactSubType;
    }

    @Before
    public void initTest() {
        contactSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactSubType() throws Exception {
        int databaseSizeBeforeCreate = contactSubTypeRepository.findAll().size();

        // Create the ContactSubType
        ContactSubTypeDTO contactSubTypeDTO = contactSubTypeMapper.toDto(contactSubType);
        restContactSubTypeMockMvc.perform(post("/api/contact-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactSubType in the database
        List<ContactSubType> contactSubTypeList = contactSubTypeRepository.findAll();
        assertThat(contactSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactSubType testContactSubType = contactSubTypeList.get(contactSubTypeList.size() - 1);
        assertThat(testContactSubType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContactSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactSubTypeRepository.findAll().size();

        // Create the ContactSubType with an existing ID
        contactSubType.setId(1L);
        ContactSubTypeDTO contactSubTypeDTO = contactSubTypeMapper.toDto(contactSubType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactSubTypeMockMvc.perform(post("/api/contact-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactSubType in the database
        List<ContactSubType> contactSubTypeList = contactSubTypeRepository.findAll();
        assertThat(contactSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactSubTypeRepository.findAll().size();
        // set the field null
        contactSubType.setName(null);

        // Create the ContactSubType, which fails.
        ContactSubTypeDTO contactSubTypeDTO = contactSubTypeMapper.toDto(contactSubType);

        restContactSubTypeMockMvc.perform(post("/api/contact-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ContactSubType> contactSubTypeList = contactSubTypeRepository.findAll();
        assertThat(contactSubTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactSubTypes() throws Exception {
        // Initialize the database
        contactSubTypeRepository.saveAndFlush(contactSubType);

        // Get all the contactSubTypeList
        restContactSubTypeMockMvc.perform(get("/api/contact-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContactSubType() throws Exception {
        // Initialize the database
        contactSubTypeRepository.saveAndFlush(contactSubType);

        // Get the contactSubType
        restContactSubTypeMockMvc.perform(get("/api/contact-sub-types/{id}", contactSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactSubType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactSubType() throws Exception {
        // Get the contactSubType
        restContactSubTypeMockMvc.perform(get("/api/contact-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactSubType() throws Exception {
        // Initialize the database
        contactSubTypeRepository.saveAndFlush(contactSubType);

        int databaseSizeBeforeUpdate = contactSubTypeRepository.findAll().size();

        // Update the contactSubType
        ContactSubType updatedContactSubType = contactSubTypeRepository.findById(contactSubType.getId()).get();
        // Disconnect from session so that the updates on updatedContactSubType are not directly saved in db
        em.detach(updatedContactSubType);
        updatedContactSubType
            .name(UPDATED_NAME);
        ContactSubTypeDTO contactSubTypeDTO = contactSubTypeMapper.toDto(updatedContactSubType);

        restContactSubTypeMockMvc.perform(put("/api/contact-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ContactSubType in the database
        List<ContactSubType> contactSubTypeList = contactSubTypeRepository.findAll();
        assertThat(contactSubTypeList).hasSize(databaseSizeBeforeUpdate);
        ContactSubType testContactSubType = contactSubTypeList.get(contactSubTypeList.size() - 1);
        assertThat(testContactSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContactSubType() throws Exception {
        int databaseSizeBeforeUpdate = contactSubTypeRepository.findAll().size();

        // Create the ContactSubType
        ContactSubTypeDTO contactSubTypeDTO = contactSubTypeMapper.toDto(contactSubType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactSubTypeMockMvc.perform(put("/api/contact-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSubTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactSubType in the database
        List<ContactSubType> contactSubTypeList = contactSubTypeRepository.findAll();
        assertThat(contactSubTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactSubType() throws Exception {
        // Initialize the database
        contactSubTypeRepository.saveAndFlush(contactSubType);

        int databaseSizeBeforeDelete = contactSubTypeRepository.findAll().size();

        // Delete the contactSubType
        restContactSubTypeMockMvc.perform(delete("/api/contact-sub-types/{id}", contactSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactSubType> contactSubTypeList = contactSubTypeRepository.findAll();
        assertThat(contactSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSubType.class);
        ContactSubType contactSubType1 = new ContactSubType();
        contactSubType1.setId(1L);
        ContactSubType contactSubType2 = new ContactSubType();
        contactSubType2.setId(contactSubType1.getId());
        assertThat(contactSubType1).isEqualTo(contactSubType2);
        contactSubType2.setId(2L);
        assertThat(contactSubType1).isNotEqualTo(contactSubType2);
        contactSubType1.setId(null);
        assertThat(contactSubType1).isNotEqualTo(contactSubType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSubTypeDTO.class);
        ContactSubTypeDTO contactSubTypeDTO1 = new ContactSubTypeDTO();
        contactSubTypeDTO1.setId(1L);
        ContactSubTypeDTO contactSubTypeDTO2 = new ContactSubTypeDTO();
        assertThat(contactSubTypeDTO1).isNotEqualTo(contactSubTypeDTO2);
        contactSubTypeDTO2.setId(contactSubTypeDTO1.getId());
        assertThat(contactSubTypeDTO1).isEqualTo(contactSubTypeDTO2);
        contactSubTypeDTO2.setId(2L);
        assertThat(contactSubTypeDTO1).isNotEqualTo(contactSubTypeDTO2);
        contactSubTypeDTO1.setId(null);
        assertThat(contactSubTypeDTO1).isNotEqualTo(contactSubTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactSubTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactSubTypeMapper.fromId(null)).isNull();
    }
}
