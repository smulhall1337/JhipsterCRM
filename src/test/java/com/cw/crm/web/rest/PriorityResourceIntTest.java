package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.Priority;
import com.cw.crm.repository.PriorityRepository;
import com.cw.crm.service.PriorityService;
import com.cw.crm.service.dto.PriorityDTO;
import com.cw.crm.service.mapper.PriorityMapper;
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
 * Test class for the PriorityResource REST controller.
 *
 * @see PriorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class PriorityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private PriorityMapper priorityMapper;

    @Autowired
    private PriorityService priorityService;

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

    private MockMvc restPriorityMockMvc;

    private Priority priority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriorityResource priorityResource = new PriorityResource(priorityService);
        this.restPriorityMockMvc = MockMvcBuilders.standaloneSetup(priorityResource)
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
    public static Priority createEntity(EntityManager em) {
        Priority priority = new Priority()
            .name(DEFAULT_NAME);
        return priority;
    }

    @Before
    public void initTest() {
        priority = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriority() throws Exception {
        int databaseSizeBeforeCreate = priorityRepository.findAll().size();

        // Create the Priority
        PriorityDTO priorityDTO = priorityMapper.toDto(priority);
        restPriorityMockMvc.perform(post("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priorityDTO)))
            .andExpect(status().isCreated());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeCreate + 1);
        Priority testPriority = priorityList.get(priorityList.size() - 1);
        assertThat(testPriority.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPriorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priorityRepository.findAll().size();

        // Create the Priority with an existing ID
        priority.setId(1L);
        PriorityDTO priorityDTO = priorityMapper.toDto(priority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriorityMockMvc.perform(post("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = priorityRepository.findAll().size();
        // set the field null
        priority.setName(null);

        // Create the Priority, which fails.
        PriorityDTO priorityDTO = priorityMapper.toDto(priority);

        restPriorityMockMvc.perform(post("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priorityDTO)))
            .andExpect(status().isBadRequest());

        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriorities() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        // Get all the priorityList
        restPriorityMockMvc.perform(get("/api/priorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getPriority() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        // Get the priority
        restPriorityMockMvc.perform(get("/api/priorities/{id}", priority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priority.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriority() throws Exception {
        // Get the priority
        restPriorityMockMvc.perform(get("/api/priorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriority() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        int databaseSizeBeforeUpdate = priorityRepository.findAll().size();

        // Update the priority
        Priority updatedPriority = priorityRepository.findById(priority.getId()).get();
        // Disconnect from session so that the updates on updatedPriority are not directly saved in db
        em.detach(updatedPriority);
        updatedPriority
            .name(UPDATED_NAME);
        PriorityDTO priorityDTO = priorityMapper.toDto(updatedPriority);

        restPriorityMockMvc.perform(put("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priorityDTO)))
            .andExpect(status().isOk());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeUpdate);
        Priority testPriority = priorityList.get(priorityList.size() - 1);
        assertThat(testPriority.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPriority() throws Exception {
        int databaseSizeBeforeUpdate = priorityRepository.findAll().size();

        // Create the Priority
        PriorityDTO priorityDTO = priorityMapper.toDto(priority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriorityMockMvc.perform(put("/api/priorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Priority in the database
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriority() throws Exception {
        // Initialize the database
        priorityRepository.saveAndFlush(priority);

        int databaseSizeBeforeDelete = priorityRepository.findAll().size();

        // Get the priority
        restPriorityMockMvc.perform(delete("/api/priorities/{id}", priority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Priority> priorityList = priorityRepository.findAll();
        assertThat(priorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Priority.class);
        Priority priority1 = new Priority();
        priority1.setId(1L);
        Priority priority2 = new Priority();
        priority2.setId(priority1.getId());
        assertThat(priority1).isEqualTo(priority2);
        priority2.setId(2L);
        assertThat(priority1).isNotEqualTo(priority2);
        priority1.setId(null);
        assertThat(priority1).isNotEqualTo(priority2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriorityDTO.class);
        PriorityDTO priorityDTO1 = new PriorityDTO();
        priorityDTO1.setId(1L);
        PriorityDTO priorityDTO2 = new PriorityDTO();
        assertThat(priorityDTO1).isNotEqualTo(priorityDTO2);
        priorityDTO2.setId(priorityDTO1.getId());
        assertThat(priorityDTO1).isEqualTo(priorityDTO2);
        priorityDTO2.setId(2L);
        assertThat(priorityDTO1).isNotEqualTo(priorityDTO2);
        priorityDTO1.setId(null);
        assertThat(priorityDTO1).isNotEqualTo(priorityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priorityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priorityMapper.fromId(null)).isNull();
    }
}
