package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.EnrollmentAgency;
import com.cw.crm.repository.EnrollmentAgencyRepository;
import com.cw.crm.service.EnrollmentAgencyService;
import com.cw.crm.service.dto.EnrollmentAgencyDTO;
import com.cw.crm.service.mapper.EnrollmentAgencyMapper;
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
 * Test class for the EnrollmentAgencyResource REST controller.
 *
 * @see EnrollmentAgencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class EnrollmentAgencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_RECORD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private EnrollmentAgencyRepository enrollmentAgencyRepository;

    @Autowired
    private EnrollmentAgencyMapper enrollmentAgencyMapper;

    @Autowired
    private EnrollmentAgencyService enrollmentAgencyService;

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

    private MockMvc restEnrollmentAgencyMockMvc;

    private EnrollmentAgency enrollmentAgency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnrollmentAgencyResource enrollmentAgencyResource = new EnrollmentAgencyResource(enrollmentAgencyService);
        this.restEnrollmentAgencyMockMvc = MockMvcBuilders.standaloneSetup(enrollmentAgencyResource)
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
    public static EnrollmentAgency createEntity(EntityManager em) {
        EnrollmentAgency enrollmentAgency = new EnrollmentAgency()
            .name(DEFAULT_NAME)
            .office(DEFAULT_OFFICE)
            .recordNumber(DEFAULT_RECORD_NUMBER)
            .phone(DEFAULT_PHONE);
        return enrollmentAgency;
    }

    @Before
    public void initTest() {
        enrollmentAgency = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollmentAgency() throws Exception {
        int databaseSizeBeforeCreate = enrollmentAgencyRepository.findAll().size();

        // Create the EnrollmentAgency
        EnrollmentAgencyDTO enrollmentAgencyDTO = enrollmentAgencyMapper.toDto(enrollmentAgency);
        restEnrollmentAgencyMockMvc.perform(post("/api/enrollment-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentAgencyDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentAgency in the database
        List<EnrollmentAgency> enrollmentAgencyList = enrollmentAgencyRepository.findAll();
        assertThat(enrollmentAgencyList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollmentAgency testEnrollmentAgency = enrollmentAgencyList.get(enrollmentAgencyList.size() - 1);
        assertThat(testEnrollmentAgency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnrollmentAgency.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testEnrollmentAgency.getRecordNumber()).isEqualTo(DEFAULT_RECORD_NUMBER);
        assertThat(testEnrollmentAgency.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createEnrollmentAgencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentAgencyRepository.findAll().size();

        // Create the EnrollmentAgency with an existing ID
        enrollmentAgency.setId(1L);
        EnrollmentAgencyDTO enrollmentAgencyDTO = enrollmentAgencyMapper.toDto(enrollmentAgency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentAgencyMockMvc.perform(post("/api/enrollment-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentAgencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentAgency in the database
        List<EnrollmentAgency> enrollmentAgencyList = enrollmentAgencyRepository.findAll();
        assertThat(enrollmentAgencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnrollmentAgencies() throws Exception {
        // Initialize the database
        enrollmentAgencyRepository.saveAndFlush(enrollmentAgency);

        // Get all the enrollmentAgencyList
        restEnrollmentAgencyMockMvc.perform(get("/api/enrollment-agencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentAgency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].recordNumber").value(hasItem(DEFAULT_RECORD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnrollmentAgency() throws Exception {
        // Initialize the database
        enrollmentAgencyRepository.saveAndFlush(enrollmentAgency);

        // Get the enrollmentAgency
        restEnrollmentAgencyMockMvc.perform(get("/api/enrollment-agencies/{id}", enrollmentAgency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enrollmentAgency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE.toString()))
            .andExpect(jsonPath("$.recordNumber").value(DEFAULT_RECORD_NUMBER.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnrollmentAgency() throws Exception {
        // Get the enrollmentAgency
        restEnrollmentAgencyMockMvc.perform(get("/api/enrollment-agencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollmentAgency() throws Exception {
        // Initialize the database
        enrollmentAgencyRepository.saveAndFlush(enrollmentAgency);

        int databaseSizeBeforeUpdate = enrollmentAgencyRepository.findAll().size();

        // Update the enrollmentAgency
        EnrollmentAgency updatedEnrollmentAgency = enrollmentAgencyRepository.findById(enrollmentAgency.getId()).get();
        // Disconnect from session so that the updates on updatedEnrollmentAgency are not directly saved in db
        em.detach(updatedEnrollmentAgency);
        updatedEnrollmentAgency
            .name(UPDATED_NAME)
            .office(UPDATED_OFFICE)
            .recordNumber(UPDATED_RECORD_NUMBER)
            .phone(UPDATED_PHONE);
        EnrollmentAgencyDTO enrollmentAgencyDTO = enrollmentAgencyMapper.toDto(updatedEnrollmentAgency);

        restEnrollmentAgencyMockMvc.perform(put("/api/enrollment-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentAgencyDTO)))
            .andExpect(status().isOk());

        // Validate the EnrollmentAgency in the database
        List<EnrollmentAgency> enrollmentAgencyList = enrollmentAgencyRepository.findAll();
        assertThat(enrollmentAgencyList).hasSize(databaseSizeBeforeUpdate);
        EnrollmentAgency testEnrollmentAgency = enrollmentAgencyList.get(enrollmentAgencyList.size() - 1);
        assertThat(testEnrollmentAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnrollmentAgency.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testEnrollmentAgency.getRecordNumber()).isEqualTo(UPDATED_RECORD_NUMBER);
        assertThat(testEnrollmentAgency.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollmentAgency() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentAgencyRepository.findAll().size();

        // Create the EnrollmentAgency
        EnrollmentAgencyDTO enrollmentAgencyDTO = enrollmentAgencyMapper.toDto(enrollmentAgency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnrollmentAgencyMockMvc.perform(put("/api/enrollment-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentAgencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentAgency in the database
        List<EnrollmentAgency> enrollmentAgencyList = enrollmentAgencyRepository.findAll();
        assertThat(enrollmentAgencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnrollmentAgency() throws Exception {
        // Initialize the database
        enrollmentAgencyRepository.saveAndFlush(enrollmentAgency);

        int databaseSizeBeforeDelete = enrollmentAgencyRepository.findAll().size();

        // Get the enrollmentAgency
        restEnrollmentAgencyMockMvc.perform(delete("/api/enrollment-agencies/{id}", enrollmentAgency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EnrollmentAgency> enrollmentAgencyList = enrollmentAgencyRepository.findAll();
        assertThat(enrollmentAgencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentAgency.class);
        EnrollmentAgency enrollmentAgency1 = new EnrollmentAgency();
        enrollmentAgency1.setId(1L);
        EnrollmentAgency enrollmentAgency2 = new EnrollmentAgency();
        enrollmentAgency2.setId(enrollmentAgency1.getId());
        assertThat(enrollmentAgency1).isEqualTo(enrollmentAgency2);
        enrollmentAgency2.setId(2L);
        assertThat(enrollmentAgency1).isNotEqualTo(enrollmentAgency2);
        enrollmentAgency1.setId(null);
        assertThat(enrollmentAgency1).isNotEqualTo(enrollmentAgency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentAgencyDTO.class);
        EnrollmentAgencyDTO enrollmentAgencyDTO1 = new EnrollmentAgencyDTO();
        enrollmentAgencyDTO1.setId(1L);
        EnrollmentAgencyDTO enrollmentAgencyDTO2 = new EnrollmentAgencyDTO();
        assertThat(enrollmentAgencyDTO1).isNotEqualTo(enrollmentAgencyDTO2);
        enrollmentAgencyDTO2.setId(enrollmentAgencyDTO1.getId());
        assertThat(enrollmentAgencyDTO1).isEqualTo(enrollmentAgencyDTO2);
        enrollmentAgencyDTO2.setId(2L);
        assertThat(enrollmentAgencyDTO1).isNotEqualTo(enrollmentAgencyDTO2);
        enrollmentAgencyDTO1.setId(null);
        assertThat(enrollmentAgencyDTO1).isNotEqualTo(enrollmentAgencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enrollmentAgencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enrollmentAgencyMapper.fromId(null)).isNull();
    }
}
