package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ReferralType;
import com.cw.crm.repository.ReferralTypeRepository;
import com.cw.crm.service.ReferralTypeService;
import com.cw.crm.service.dto.ReferralTypeDTO;
import com.cw.crm.service.mapper.ReferralTypeMapper;
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
 * Test class for the ReferralTypeResource REST controller.
 *
 * @see ReferralTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ReferralTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ReferralTypeRepository referralTypeRepository;

    @Autowired
    private ReferralTypeMapper referralTypeMapper;

    @Autowired
    private ReferralTypeService referralTypeService;

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

    private MockMvc restReferralTypeMockMvc;

    private ReferralType referralType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferralTypeResource referralTypeResource = new ReferralTypeResource(referralTypeService);
        this.restReferralTypeMockMvc = MockMvcBuilders.standaloneSetup(referralTypeResource)
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
    public static ReferralType createEntity(EntityManager em) {
        ReferralType referralType = new ReferralType()
            .name(DEFAULT_NAME);
        return referralType;
    }

    @Before
    public void initTest() {
        referralType = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferralType() throws Exception {
        int databaseSizeBeforeCreate = referralTypeRepository.findAll().size();

        // Create the ReferralType
        ReferralTypeDTO referralTypeDTO = referralTypeMapper.toDto(referralType);
        restReferralTypeMockMvc.perform(post("/api/referral-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ReferralType in the database
        List<ReferralType> referralTypeList = referralTypeRepository.findAll();
        assertThat(referralTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ReferralType testReferralType = referralTypeList.get(referralTypeList.size() - 1);
        assertThat(testReferralType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createReferralTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referralTypeRepository.findAll().size();

        // Create the ReferralType with an existing ID
        referralType.setId(1L);
        ReferralTypeDTO referralTypeDTO = referralTypeMapper.toDto(referralType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferralTypeMockMvc.perform(post("/api/referral-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferralType in the database
        List<ReferralType> referralTypeList = referralTypeRepository.findAll();
        assertThat(referralTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = referralTypeRepository.findAll().size();
        // set the field null
        referralType.setName(null);

        // Create the ReferralType, which fails.
        ReferralTypeDTO referralTypeDTO = referralTypeMapper.toDto(referralType);

        restReferralTypeMockMvc.perform(post("/api/referral-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ReferralType> referralTypeList = referralTypeRepository.findAll();
        assertThat(referralTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReferralTypes() throws Exception {
        // Initialize the database
        referralTypeRepository.saveAndFlush(referralType);

        // Get all the referralTypeList
        restReferralTypeMockMvc.perform(get("/api/referral-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referralType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getReferralType() throws Exception {
        // Initialize the database
        referralTypeRepository.saveAndFlush(referralType);

        // Get the referralType
        restReferralTypeMockMvc.perform(get("/api/referral-types/{id}", referralType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referralType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferralType() throws Exception {
        // Get the referralType
        restReferralTypeMockMvc.perform(get("/api/referral-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferralType() throws Exception {
        // Initialize the database
        referralTypeRepository.saveAndFlush(referralType);

        int databaseSizeBeforeUpdate = referralTypeRepository.findAll().size();

        // Update the referralType
        ReferralType updatedReferralType = referralTypeRepository.findById(referralType.getId()).get();
        // Disconnect from session so that the updates on updatedReferralType are not directly saved in db
        em.detach(updatedReferralType);
        updatedReferralType
            .name(UPDATED_NAME);
        ReferralTypeDTO referralTypeDTO = referralTypeMapper.toDto(updatedReferralType);

        restReferralTypeMockMvc.perform(put("/api/referral-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ReferralType in the database
        List<ReferralType> referralTypeList = referralTypeRepository.findAll();
        assertThat(referralTypeList).hasSize(databaseSizeBeforeUpdate);
        ReferralType testReferralType = referralTypeList.get(referralTypeList.size() - 1);
        assertThat(testReferralType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingReferralType() throws Exception {
        int databaseSizeBeforeUpdate = referralTypeRepository.findAll().size();

        // Create the ReferralType
        ReferralTypeDTO referralTypeDTO = referralTypeMapper.toDto(referralType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferralTypeMockMvc.perform(put("/api/referral-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferralType in the database
        List<ReferralType> referralTypeList = referralTypeRepository.findAll();
        assertThat(referralTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReferralType() throws Exception {
        // Initialize the database
        referralTypeRepository.saveAndFlush(referralType);

        int databaseSizeBeforeDelete = referralTypeRepository.findAll().size();

        // Get the referralType
        restReferralTypeMockMvc.perform(delete("/api/referral-types/{id}", referralType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReferralType> referralTypeList = referralTypeRepository.findAll();
        assertThat(referralTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralType.class);
        ReferralType referralType1 = new ReferralType();
        referralType1.setId(1L);
        ReferralType referralType2 = new ReferralType();
        referralType2.setId(referralType1.getId());
        assertThat(referralType1).isEqualTo(referralType2);
        referralType2.setId(2L);
        assertThat(referralType1).isNotEqualTo(referralType2);
        referralType1.setId(null);
        assertThat(referralType1).isNotEqualTo(referralType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralTypeDTO.class);
        ReferralTypeDTO referralTypeDTO1 = new ReferralTypeDTO();
        referralTypeDTO1.setId(1L);
        ReferralTypeDTO referralTypeDTO2 = new ReferralTypeDTO();
        assertThat(referralTypeDTO1).isNotEqualTo(referralTypeDTO2);
        referralTypeDTO2.setId(referralTypeDTO1.getId());
        assertThat(referralTypeDTO1).isEqualTo(referralTypeDTO2);
        referralTypeDTO2.setId(2L);
        assertThat(referralTypeDTO1).isNotEqualTo(referralTypeDTO2);
        referralTypeDTO1.setId(null);
        assertThat(referralTypeDTO1).isNotEqualTo(referralTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referralTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referralTypeMapper.fromId(null)).isNull();
    }
}
