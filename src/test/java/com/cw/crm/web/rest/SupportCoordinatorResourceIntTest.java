package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.SupportCoordinator;
import com.cw.crm.domain.Department;
import com.cw.crm.domain.EmployeeType;
import com.cw.crm.domain.EmployeeSubType;
import com.cw.crm.repository.SupportCoordinatorRepository;
import com.cw.crm.service.SupportCoordinatorService;
import com.cw.crm.service.dto.SupportCoordinatorDTO;
import com.cw.crm.service.mapper.SupportCoordinatorMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.cw.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SupportCoordinatorResource REST controller.
 *
 * @see SupportCoordinatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class SupportCoordinatorResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_HIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_HIRED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private SupportCoordinatorRepository supportCoordinatorRepository;

    @Autowired
    private SupportCoordinatorMapper supportCoordinatorMapper;

    @Autowired
    private SupportCoordinatorService supportCoordinatorService;

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

    private MockMvc restSupportCoordinatorMockMvc;

    private SupportCoordinator supportCoordinator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupportCoordinatorResource supportCoordinatorResource = new SupportCoordinatorResource(supportCoordinatorService);
        this.restSupportCoordinatorMockMvc = MockMvcBuilders.standaloneSetup(supportCoordinatorResource)
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
    public static SupportCoordinator createEntity(EntityManager em) {
        SupportCoordinator supportCoordinator = new SupportCoordinator()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .emailId(DEFAULT_EMAIL_ID)
            .dateHired(DEFAULT_DATE_HIRED)
            .userName(DEFAULT_USER_NAME);
        // Add required entity
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        supportCoordinator.setDepartment(department);
        // Add required entity
        EmployeeType employeeType = EmployeeTypeResourceIntTest.createEntity(em);
        em.persist(employeeType);
        em.flush();
        supportCoordinator.setEmployeeType(employeeType);
        // Add required entity
        EmployeeSubType employeeSubType = EmployeeSubTypeResourceIntTest.createEntity(em);
        em.persist(employeeSubType);
        em.flush();
        supportCoordinator.setEmployeeSubType(employeeSubType);
        return supportCoordinator;
    }

    @Before
    public void initTest() {
        supportCoordinator = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupportCoordinator() throws Exception {
        int databaseSizeBeforeCreate = supportCoordinatorRepository.findAll().size();

        // Create the SupportCoordinator
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);
        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isCreated());

        // Validate the SupportCoordinator in the database
        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeCreate + 1);
        SupportCoordinator testSupportCoordinator = supportCoordinatorList.get(supportCoordinatorList.size() - 1);
        assertThat(testSupportCoordinator.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSupportCoordinator.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSupportCoordinator.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSupportCoordinator.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testSupportCoordinator.getDateHired()).isEqualTo(DEFAULT_DATE_HIRED);
        assertThat(testSupportCoordinator.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    public void createSupportCoordinatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supportCoordinatorRepository.findAll().size();

        // Create the SupportCoordinator with an existing ID
        supportCoordinator.setId(1L);
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupportCoordinator in the database
        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportCoordinatorRepository.findAll().size();
        // set the field null
        supportCoordinator.setFirstName(null);

        // Create the SupportCoordinator, which fails.
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportCoordinatorRepository.findAll().size();
        // set the field null
        supportCoordinator.setLastName(null);

        // Create the SupportCoordinator, which fails.
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportCoordinatorRepository.findAll().size();
        // set the field null
        supportCoordinator.setPhone(null);

        // Create the SupportCoordinator, which fails.
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportCoordinatorRepository.findAll().size();
        // set the field null
        supportCoordinator.setEmailId(null);

        // Create the SupportCoordinator, which fails.
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateHiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportCoordinatorRepository.findAll().size();
        // set the field null
        supportCoordinator.setDateHired(null);

        // Create the SupportCoordinator, which fails.
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supportCoordinatorRepository.findAll().size();
        // set the field null
        supportCoordinator.setUserName(null);

        // Create the SupportCoordinator, which fails.
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        restSupportCoordinatorMockMvc.perform(post("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupportCoordinators() throws Exception {
        // Initialize the database
        supportCoordinatorRepository.saveAndFlush(supportCoordinator);

        // Get all the supportCoordinatorList
        restSupportCoordinatorMockMvc.perform(get("/api/support-coordinators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportCoordinator.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].dateHired").value(hasItem(DEFAULT_DATE_HIRED.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSupportCoordinator() throws Exception {
        // Initialize the database
        supportCoordinatorRepository.saveAndFlush(supportCoordinator);

        // Get the supportCoordinator
        restSupportCoordinatorMockMvc.perform(get("/api/support-coordinators/{id}", supportCoordinator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supportCoordinator.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.dateHired").value(DEFAULT_DATE_HIRED.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupportCoordinator() throws Exception {
        // Get the supportCoordinator
        restSupportCoordinatorMockMvc.perform(get("/api/support-coordinators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportCoordinator() throws Exception {
        // Initialize the database
        supportCoordinatorRepository.saveAndFlush(supportCoordinator);

        int databaseSizeBeforeUpdate = supportCoordinatorRepository.findAll().size();

        // Update the supportCoordinator
        SupportCoordinator updatedSupportCoordinator = supportCoordinatorRepository.findById(supportCoordinator.getId()).get();
        // Disconnect from session so that the updates on updatedSupportCoordinator are not directly saved in db
        em.detach(updatedSupportCoordinator);
        updatedSupportCoordinator
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .emailId(UPDATED_EMAIL_ID)
            .dateHired(UPDATED_DATE_HIRED)
            .userName(UPDATED_USER_NAME);
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(updatedSupportCoordinator);

        restSupportCoordinatorMockMvc.perform(put("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isOk());

        // Validate the SupportCoordinator in the database
        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeUpdate);
        SupportCoordinator testSupportCoordinator = supportCoordinatorList.get(supportCoordinatorList.size() - 1);
        assertThat(testSupportCoordinator.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupportCoordinator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupportCoordinator.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSupportCoordinator.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testSupportCoordinator.getDateHired()).isEqualTo(UPDATED_DATE_HIRED);
        assertThat(testSupportCoordinator.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSupportCoordinator() throws Exception {
        int databaseSizeBeforeUpdate = supportCoordinatorRepository.findAll().size();

        // Create the SupportCoordinator
        SupportCoordinatorDTO supportCoordinatorDTO = supportCoordinatorMapper.toDto(supportCoordinator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportCoordinatorMockMvc.perform(put("/api/support-coordinators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supportCoordinatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupportCoordinator in the database
        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupportCoordinator() throws Exception {
        // Initialize the database
        supportCoordinatorRepository.saveAndFlush(supportCoordinator);

        int databaseSizeBeforeDelete = supportCoordinatorRepository.findAll().size();

        // Get the supportCoordinator
        restSupportCoordinatorMockMvc.perform(delete("/api/support-coordinators/{id}", supportCoordinator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupportCoordinator> supportCoordinatorList = supportCoordinatorRepository.findAll();
        assertThat(supportCoordinatorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportCoordinator.class);
        SupportCoordinator supportCoordinator1 = new SupportCoordinator();
        supportCoordinator1.setId(1L);
        SupportCoordinator supportCoordinator2 = new SupportCoordinator();
        supportCoordinator2.setId(supportCoordinator1.getId());
        assertThat(supportCoordinator1).isEqualTo(supportCoordinator2);
        supportCoordinator2.setId(2L);
        assertThat(supportCoordinator1).isNotEqualTo(supportCoordinator2);
        supportCoordinator1.setId(null);
        assertThat(supportCoordinator1).isNotEqualTo(supportCoordinator2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportCoordinatorDTO.class);
        SupportCoordinatorDTO supportCoordinatorDTO1 = new SupportCoordinatorDTO();
        supportCoordinatorDTO1.setId(1L);
        SupportCoordinatorDTO supportCoordinatorDTO2 = new SupportCoordinatorDTO();
        assertThat(supportCoordinatorDTO1).isNotEqualTo(supportCoordinatorDTO2);
        supportCoordinatorDTO2.setId(supportCoordinatorDTO1.getId());
        assertThat(supportCoordinatorDTO1).isEqualTo(supportCoordinatorDTO2);
        supportCoordinatorDTO2.setId(2L);
        assertThat(supportCoordinatorDTO1).isNotEqualTo(supportCoordinatorDTO2);
        supportCoordinatorDTO1.setId(null);
        assertThat(supportCoordinatorDTO1).isNotEqualTo(supportCoordinatorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supportCoordinatorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supportCoordinatorMapper.fromId(null)).isNull();
    }
}
