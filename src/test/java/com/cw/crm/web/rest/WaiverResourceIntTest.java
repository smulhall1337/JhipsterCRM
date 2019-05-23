package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.Waiver;
import com.cw.crm.repository.WaiverRepository;
import com.cw.crm.service.WaiverService;
import com.cw.crm.service.dto.WaiverDTO;
import com.cw.crm.service.mapper.WaiverMapper;
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
 * Test class for the WaiverResource REST controller.
 *
 * @see WaiverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class WaiverResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private WaiverRepository waiverRepository;

    @Autowired
    private WaiverMapper waiverMapper;

    @Autowired
    private WaiverService waiverService;

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

    private MockMvc restWaiverMockMvc;

    private Waiver waiver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WaiverResource waiverResource = new WaiverResource(waiverService);
        this.restWaiverMockMvc = MockMvcBuilders.standaloneSetup(waiverResource)
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
    public static Waiver createEntity(EntityManager em) {
        Waiver waiver = new Waiver()
            .name(DEFAULT_NAME);
        return waiver;
    }

    @Before
    public void initTest() {
        waiver = createEntity(em);
    }

    @Test
    @Transactional
    public void createWaiver() throws Exception {
        int databaseSizeBeforeCreate = waiverRepository.findAll().size();

        // Create the Waiver
        WaiverDTO waiverDTO = waiverMapper.toDto(waiver);
        restWaiverMockMvc.perform(post("/api/waivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waiverDTO)))
            .andExpect(status().isCreated());

        // Validate the Waiver in the database
        List<Waiver> waiverList = waiverRepository.findAll();
        assertThat(waiverList).hasSize(databaseSizeBeforeCreate + 1);
        Waiver testWaiver = waiverList.get(waiverList.size() - 1);
        assertThat(testWaiver.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWaiverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = waiverRepository.findAll().size();

        // Create the Waiver with an existing ID
        waiver.setId(1L);
        WaiverDTO waiverDTO = waiverMapper.toDto(waiver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWaiverMockMvc.perform(post("/api/waivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Waiver in the database
        List<Waiver> waiverList = waiverRepository.findAll();
        assertThat(waiverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = waiverRepository.findAll().size();
        // set the field null
        waiver.setName(null);

        // Create the Waiver, which fails.
        WaiverDTO waiverDTO = waiverMapper.toDto(waiver);

        restWaiverMockMvc.perform(post("/api/waivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waiverDTO)))
            .andExpect(status().isBadRequest());

        List<Waiver> waiverList = waiverRepository.findAll();
        assertThat(waiverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWaivers() throws Exception {
        // Initialize the database
        waiverRepository.saveAndFlush(waiver);

        // Get all the waiverList
        restWaiverMockMvc.perform(get("/api/waivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(waiver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getWaiver() throws Exception {
        // Initialize the database
        waiverRepository.saveAndFlush(waiver);

        // Get the waiver
        restWaiverMockMvc.perform(get("/api/waivers/{id}", waiver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(waiver.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWaiver() throws Exception {
        // Get the waiver
        restWaiverMockMvc.perform(get("/api/waivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWaiver() throws Exception {
        // Initialize the database
        waiverRepository.saveAndFlush(waiver);

        int databaseSizeBeforeUpdate = waiverRepository.findAll().size();

        // Update the waiver
        Waiver updatedWaiver = waiverRepository.findById(waiver.getId()).get();
        // Disconnect from session so that the updates on updatedWaiver are not directly saved in db
        em.detach(updatedWaiver);
        updatedWaiver
            .name(UPDATED_NAME);
        WaiverDTO waiverDTO = waiverMapper.toDto(updatedWaiver);

        restWaiverMockMvc.perform(put("/api/waivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waiverDTO)))
            .andExpect(status().isOk());

        // Validate the Waiver in the database
        List<Waiver> waiverList = waiverRepository.findAll();
        assertThat(waiverList).hasSize(databaseSizeBeforeUpdate);
        Waiver testWaiver = waiverList.get(waiverList.size() - 1);
        assertThat(testWaiver.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWaiver() throws Exception {
        int databaseSizeBeforeUpdate = waiverRepository.findAll().size();

        // Create the Waiver
        WaiverDTO waiverDTO = waiverMapper.toDto(waiver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWaiverMockMvc.perform(put("/api/waivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(waiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Waiver in the database
        List<Waiver> waiverList = waiverRepository.findAll();
        assertThat(waiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWaiver() throws Exception {
        // Initialize the database
        waiverRepository.saveAndFlush(waiver);

        int databaseSizeBeforeDelete = waiverRepository.findAll().size();

        // Get the waiver
        restWaiverMockMvc.perform(delete("/api/waivers/{id}", waiver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Waiver> waiverList = waiverRepository.findAll();
        assertThat(waiverList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waiver.class);
        Waiver waiver1 = new Waiver();
        waiver1.setId(1L);
        Waiver waiver2 = new Waiver();
        waiver2.setId(waiver1.getId());
        assertThat(waiver1).isEqualTo(waiver2);
        waiver2.setId(2L);
        assertThat(waiver1).isNotEqualTo(waiver2);
        waiver1.setId(null);
        assertThat(waiver1).isNotEqualTo(waiver2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WaiverDTO.class);
        WaiverDTO waiverDTO1 = new WaiverDTO();
        waiverDTO1.setId(1L);
        WaiverDTO waiverDTO2 = new WaiverDTO();
        assertThat(waiverDTO1).isNotEqualTo(waiverDTO2);
        waiverDTO2.setId(waiverDTO1.getId());
        assertThat(waiverDTO1).isEqualTo(waiverDTO2);
        waiverDTO2.setId(2L);
        assertThat(waiverDTO1).isNotEqualTo(waiverDTO2);
        waiverDTO1.setId(null);
        assertThat(waiverDTO1).isNotEqualTo(waiverDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(waiverMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(waiverMapper.fromId(null)).isNull();
    }
}
