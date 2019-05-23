package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ReferralSource;
import com.cw.crm.repository.ReferralSourceRepository;
import com.cw.crm.service.ReferralSourceService;
import com.cw.crm.service.dto.ReferralSourceDTO;
import com.cw.crm.service.mapper.ReferralSourceMapper;
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
 * Test class for the ReferralSourceResource REST controller.
 *
 * @see ReferralSourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ReferralSourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ReferralSourceRepository referralSourceRepository;

    @Autowired
    private ReferralSourceMapper referralSourceMapper;

    @Autowired
    private ReferralSourceService referralSourceService;

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

    private MockMvc restReferralSourceMockMvc;

    private ReferralSource referralSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferralSourceResource referralSourceResource = new ReferralSourceResource(referralSourceService);
        this.restReferralSourceMockMvc = MockMvcBuilders.standaloneSetup(referralSourceResource)
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
    public static ReferralSource createEntity(EntityManager em) {
        ReferralSource referralSource = new ReferralSource()
            .name(DEFAULT_NAME);
        return referralSource;
    }

    @Before
    public void initTest() {
        referralSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferralSource() throws Exception {
        int databaseSizeBeforeCreate = referralSourceRepository.findAll().size();

        // Create the ReferralSource
        ReferralSourceDTO referralSourceDTO = referralSourceMapper.toDto(referralSource);
        restReferralSourceMockMvc.perform(post("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeCreate + 1);
        ReferralSource testReferralSource = referralSourceList.get(referralSourceList.size() - 1);
        assertThat(testReferralSource.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createReferralSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referralSourceRepository.findAll().size();

        // Create the ReferralSource with an existing ID
        referralSource.setId(1L);
        ReferralSourceDTO referralSourceDTO = referralSourceMapper.toDto(referralSource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferralSourceMockMvc.perform(post("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = referralSourceRepository.findAll().size();
        // set the field null
        referralSource.setName(null);

        // Create the ReferralSource, which fails.
        ReferralSourceDTO referralSourceDTO = referralSourceMapper.toDto(referralSource);

        restReferralSourceMockMvc.perform(post("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSourceDTO)))
            .andExpect(status().isBadRequest());

        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReferralSources() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);

        // Get all the referralSourceList
        restReferralSourceMockMvc.perform(get("/api/referral-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referralSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getReferralSource() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);

        // Get the referralSource
        restReferralSourceMockMvc.perform(get("/api/referral-sources/{id}", referralSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referralSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferralSource() throws Exception {
        // Get the referralSource
        restReferralSourceMockMvc.perform(get("/api/referral-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferralSource() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);

        int databaseSizeBeforeUpdate = referralSourceRepository.findAll().size();

        // Update the referralSource
        ReferralSource updatedReferralSource = referralSourceRepository.findById(referralSource.getId()).get();
        // Disconnect from session so that the updates on updatedReferralSource are not directly saved in db
        em.detach(updatedReferralSource);
        updatedReferralSource
            .name(UPDATED_NAME);
        ReferralSourceDTO referralSourceDTO = referralSourceMapper.toDto(updatedReferralSource);

        restReferralSourceMockMvc.perform(put("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSourceDTO)))
            .andExpect(status().isOk());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeUpdate);
        ReferralSource testReferralSource = referralSourceList.get(referralSourceList.size() - 1);
        assertThat(testReferralSource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingReferralSource() throws Exception {
        int databaseSizeBeforeUpdate = referralSourceRepository.findAll().size();

        // Create the ReferralSource
        ReferralSourceDTO referralSourceDTO = referralSourceMapper.toDto(referralSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferralSourceMockMvc.perform(put("/api/referral-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referralSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferralSource in the database
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReferralSource() throws Exception {
        // Initialize the database
        referralSourceRepository.saveAndFlush(referralSource);

        int databaseSizeBeforeDelete = referralSourceRepository.findAll().size();

        // Get the referralSource
        restReferralSourceMockMvc.perform(delete("/api/referral-sources/{id}", referralSource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReferralSource> referralSourceList = referralSourceRepository.findAll();
        assertThat(referralSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralSource.class);
        ReferralSource referralSource1 = new ReferralSource();
        referralSource1.setId(1L);
        ReferralSource referralSource2 = new ReferralSource();
        referralSource2.setId(referralSource1.getId());
        assertThat(referralSource1).isEqualTo(referralSource2);
        referralSource2.setId(2L);
        assertThat(referralSource1).isNotEqualTo(referralSource2);
        referralSource1.setId(null);
        assertThat(referralSource1).isNotEqualTo(referralSource2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferralSourceDTO.class);
        ReferralSourceDTO referralSourceDTO1 = new ReferralSourceDTO();
        referralSourceDTO1.setId(1L);
        ReferralSourceDTO referralSourceDTO2 = new ReferralSourceDTO();
        assertThat(referralSourceDTO1).isNotEqualTo(referralSourceDTO2);
        referralSourceDTO2.setId(referralSourceDTO1.getId());
        assertThat(referralSourceDTO1).isEqualTo(referralSourceDTO2);
        referralSourceDTO2.setId(2L);
        assertThat(referralSourceDTO1).isNotEqualTo(referralSourceDTO2);
        referralSourceDTO1.setId(null);
        assertThat(referralSourceDTO1).isNotEqualTo(referralSourceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referralSourceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referralSourceMapper.fromId(null)).isNull();
    }
}
