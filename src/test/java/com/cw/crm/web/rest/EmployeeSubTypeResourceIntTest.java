package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.EmployeeSubType;
import com.cw.crm.repository.EmployeeSubTypeRepository;
import com.cw.crm.service.EmployeeSubTypeService;
import com.cw.crm.service.dto.EmployeeSubTypeDTO;
import com.cw.crm.service.mapper.EmployeeSubTypeMapper;
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
 * Test class for the EmployeeSubTypeResource REST controller.
 *
 * @see EmployeeSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class EmployeeSubTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EmployeeSubTypeRepository employeeSubTypeRepository;

    @Autowired
    private EmployeeSubTypeMapper employeeSubTypeMapper;

    @Autowired
    private EmployeeSubTypeService employeeSubTypeService;

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

    private MockMvc restEmployeeSubTypeMockMvc;

    private EmployeeSubType employeeSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeSubTypeResource employeeSubTypeResource = new EmployeeSubTypeResource(employeeSubTypeService);
        this.restEmployeeSubTypeMockMvc = MockMvcBuilders.standaloneSetup(employeeSubTypeResource)
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
    public static EmployeeSubType createEntity(EntityManager em) {
        EmployeeSubType employeeSubType = new EmployeeSubType()
            .name(DEFAULT_NAME);
        return employeeSubType;
    }

    @Before
    public void initTest() {
        employeeSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSubType() throws Exception {
        int databaseSizeBeforeCreate = employeeSubTypeRepository.findAll().size();

        // Create the EmployeeSubType
        EmployeeSubTypeDTO employeeSubTypeDTO = employeeSubTypeMapper.toDto(employeeSubType);
        restEmployeeSubTypeMockMvc.perform(post("/api/employee-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSubTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSubType in the database
        List<EmployeeSubType> employeeSubTypeList = employeeSubTypeRepository.findAll();
        assertThat(employeeSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSubType testEmployeeSubType = employeeSubTypeList.get(employeeSubTypeList.size() - 1);
        assertThat(testEmployeeSubType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEmployeeSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeSubTypeRepository.findAll().size();

        // Create the EmployeeSubType with an existing ID
        employeeSubType.setId(1L);
        EmployeeSubTypeDTO employeeSubTypeDTO = employeeSubTypeMapper.toDto(employeeSubType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSubTypeMockMvc.perform(post("/api/employee-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSubTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSubType in the database
        List<EmployeeSubType> employeeSubTypeList = employeeSubTypeRepository.findAll();
        assertThat(employeeSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSubTypeRepository.findAll().size();
        // set the field null
        employeeSubType.setName(null);

        // Create the EmployeeSubType, which fails.
        EmployeeSubTypeDTO employeeSubTypeDTO = employeeSubTypeMapper.toDto(employeeSubType);

        restEmployeeSubTypeMockMvc.perform(post("/api/employee-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSubTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeSubType> employeeSubTypeList = employeeSubTypeRepository.findAll();
        assertThat(employeeSubTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeSubTypes() throws Exception {
        // Initialize the database
        employeeSubTypeRepository.saveAndFlush(employeeSubType);

        // Get all the employeeSubTypeList
        restEmployeeSubTypeMockMvc.perform(get("/api/employee-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeSubType() throws Exception {
        // Initialize the database
        employeeSubTypeRepository.saveAndFlush(employeeSubType);

        // Get the employeeSubType
        restEmployeeSubTypeMockMvc.perform(get("/api/employee-sub-types/{id}", employeeSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSubType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeSubType() throws Exception {
        // Get the employeeSubType
        restEmployeeSubTypeMockMvc.perform(get("/api/employee-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSubType() throws Exception {
        // Initialize the database
        employeeSubTypeRepository.saveAndFlush(employeeSubType);

        int databaseSizeBeforeUpdate = employeeSubTypeRepository.findAll().size();

        // Update the employeeSubType
        EmployeeSubType updatedEmployeeSubType = employeeSubTypeRepository.findById(employeeSubType.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSubType are not directly saved in db
        em.detach(updatedEmployeeSubType);
        updatedEmployeeSubType
            .name(UPDATED_NAME);
        EmployeeSubTypeDTO employeeSubTypeDTO = employeeSubTypeMapper.toDto(updatedEmployeeSubType);

        restEmployeeSubTypeMockMvc.perform(put("/api/employee-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSubTypeDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeSubType in the database
        List<EmployeeSubType> employeeSubTypeList = employeeSubTypeRepository.findAll();
        assertThat(employeeSubTypeList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSubType testEmployeeSubType = employeeSubTypeList.get(employeeSubTypeList.size() - 1);
        assertThat(testEmployeeSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSubType() throws Exception {
        int databaseSizeBeforeUpdate = employeeSubTypeRepository.findAll().size();

        // Create the EmployeeSubType
        EmployeeSubTypeDTO employeeSubTypeDTO = employeeSubTypeMapper.toDto(employeeSubType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSubTypeMockMvc.perform(put("/api/employee-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSubTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSubType in the database
        List<EmployeeSubType> employeeSubTypeList = employeeSubTypeRepository.findAll();
        assertThat(employeeSubTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSubType() throws Exception {
        // Initialize the database
        employeeSubTypeRepository.saveAndFlush(employeeSubType);

        int databaseSizeBeforeDelete = employeeSubTypeRepository.findAll().size();

        // Get the employeeSubType
        restEmployeeSubTypeMockMvc.perform(delete("/api/employee-sub-types/{id}", employeeSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeSubType> employeeSubTypeList = employeeSubTypeRepository.findAll();
        assertThat(employeeSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSubType.class);
        EmployeeSubType employeeSubType1 = new EmployeeSubType();
        employeeSubType1.setId(1L);
        EmployeeSubType employeeSubType2 = new EmployeeSubType();
        employeeSubType2.setId(employeeSubType1.getId());
        assertThat(employeeSubType1).isEqualTo(employeeSubType2);
        employeeSubType2.setId(2L);
        assertThat(employeeSubType1).isNotEqualTo(employeeSubType2);
        employeeSubType1.setId(null);
        assertThat(employeeSubType1).isNotEqualTo(employeeSubType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSubTypeDTO.class);
        EmployeeSubTypeDTO employeeSubTypeDTO1 = new EmployeeSubTypeDTO();
        employeeSubTypeDTO1.setId(1L);
        EmployeeSubTypeDTO employeeSubTypeDTO2 = new EmployeeSubTypeDTO();
        assertThat(employeeSubTypeDTO1).isNotEqualTo(employeeSubTypeDTO2);
        employeeSubTypeDTO2.setId(employeeSubTypeDTO1.getId());
        assertThat(employeeSubTypeDTO1).isEqualTo(employeeSubTypeDTO2);
        employeeSubTypeDTO2.setId(2L);
        assertThat(employeeSubTypeDTO1).isNotEqualTo(employeeSubTypeDTO2);
        employeeSubTypeDTO1.setId(null);
        assertThat(employeeSubTypeDTO1).isNotEqualTo(employeeSubTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeSubTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeSubTypeMapper.fromId(null)).isNull();
    }
}
