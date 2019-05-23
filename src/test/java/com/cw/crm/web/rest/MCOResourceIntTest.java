package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.MCO;
import com.cw.crm.repository.MCORepository;
import com.cw.crm.service.MCOService;
import com.cw.crm.service.dto.MCODTO;
import com.cw.crm.service.mapper.MCOMapper;
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
 * Test class for the MCOResource REST controller.
 *
 * @see MCOResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class MCOResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MCORepository mCORepository;

    @Autowired
    private MCOMapper mCOMapper;

    @Autowired
    private MCOService mCOService;

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

    private MockMvc restMCOMockMvc;

    private MCO mCO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MCOResource mCOResource = new MCOResource(mCOService);
        this.restMCOMockMvc = MockMvcBuilders.standaloneSetup(mCOResource)
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
    public static MCO createEntity(EntityManager em) {
        MCO mCO = new MCO()
            .name(DEFAULT_NAME);
        return mCO;
    }

    @Before
    public void initTest() {
        mCO = createEntity(em);
    }

    @Test
    @Transactional
    public void createMCO() throws Exception {
        int databaseSizeBeforeCreate = mCORepository.findAll().size();

        // Create the MCO
        MCODTO mCODTO = mCOMapper.toDto(mCO);
        restMCOMockMvc.perform(post("/api/mcos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCODTO)))
            .andExpect(status().isCreated());

        // Validate the MCO in the database
        List<MCO> mCOList = mCORepository.findAll();
        assertThat(mCOList).hasSize(databaseSizeBeforeCreate + 1);
        MCO testMCO = mCOList.get(mCOList.size() - 1);
        assertThat(testMCO.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMCOWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mCORepository.findAll().size();

        // Create the MCO with an existing ID
        mCO.setId(1L);
        MCODTO mCODTO = mCOMapper.toDto(mCO);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMCOMockMvc.perform(post("/api/mcos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCODTO)))
            .andExpect(status().isBadRequest());

        // Validate the MCO in the database
        List<MCO> mCOList = mCORepository.findAll();
        assertThat(mCOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mCORepository.findAll().size();
        // set the field null
        mCO.setName(null);

        // Create the MCO, which fails.
        MCODTO mCODTO = mCOMapper.toDto(mCO);

        restMCOMockMvc.perform(post("/api/mcos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCODTO)))
            .andExpect(status().isBadRequest());

        List<MCO> mCOList = mCORepository.findAll();
        assertThat(mCOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMCOS() throws Exception {
        // Initialize the database
        mCORepository.saveAndFlush(mCO);

        // Get all the mCOList
        restMCOMockMvc.perform(get("/api/mcos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mCO.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMCO() throws Exception {
        // Initialize the database
        mCORepository.saveAndFlush(mCO);

        // Get the mCO
        restMCOMockMvc.perform(get("/api/mcos/{id}", mCO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mCO.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMCO() throws Exception {
        // Get the mCO
        restMCOMockMvc.perform(get("/api/mcos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMCO() throws Exception {
        // Initialize the database
        mCORepository.saveAndFlush(mCO);

        int databaseSizeBeforeUpdate = mCORepository.findAll().size();

        // Update the mCO
        MCO updatedMCO = mCORepository.findById(mCO.getId()).get();
        // Disconnect from session so that the updates on updatedMCO are not directly saved in db
        em.detach(updatedMCO);
        updatedMCO
            .name(UPDATED_NAME);
        MCODTO mCODTO = mCOMapper.toDto(updatedMCO);

        restMCOMockMvc.perform(put("/api/mcos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCODTO)))
            .andExpect(status().isOk());

        // Validate the MCO in the database
        List<MCO> mCOList = mCORepository.findAll();
        assertThat(mCOList).hasSize(databaseSizeBeforeUpdate);
        MCO testMCO = mCOList.get(mCOList.size() - 1);
        assertThat(testMCO.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMCO() throws Exception {
        int databaseSizeBeforeUpdate = mCORepository.findAll().size();

        // Create the MCO
        MCODTO mCODTO = mCOMapper.toDto(mCO);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMCOMockMvc.perform(put("/api/mcos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mCODTO)))
            .andExpect(status().isBadRequest());

        // Validate the MCO in the database
        List<MCO> mCOList = mCORepository.findAll();
        assertThat(mCOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMCO() throws Exception {
        // Initialize the database
        mCORepository.saveAndFlush(mCO);

        int databaseSizeBeforeDelete = mCORepository.findAll().size();

        // Get the mCO
        restMCOMockMvc.perform(delete("/api/mcos/{id}", mCO.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MCO> mCOList = mCORepository.findAll();
        assertThat(mCOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MCO.class);
        MCO mCO1 = new MCO();
        mCO1.setId(1L);
        MCO mCO2 = new MCO();
        mCO2.setId(mCO1.getId());
        assertThat(mCO1).isEqualTo(mCO2);
        mCO2.setId(2L);
        assertThat(mCO1).isNotEqualTo(mCO2);
        mCO1.setId(null);
        assertThat(mCO1).isNotEqualTo(mCO2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MCODTO.class);
        MCODTO mCODTO1 = new MCODTO();
        mCODTO1.setId(1L);
        MCODTO mCODTO2 = new MCODTO();
        assertThat(mCODTO1).isNotEqualTo(mCODTO2);
        mCODTO2.setId(mCODTO1.getId());
        assertThat(mCODTO1).isEqualTo(mCODTO2);
        mCODTO2.setId(2L);
        assertThat(mCODTO1).isNotEqualTo(mCODTO2);
        mCODTO1.setId(null);
        assertThat(mCODTO1).isNotEqualTo(mCODTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mCOMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mCOMapper.fromId(null)).isNull();
    }
}
