package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ActionStatus;
import com.cw.crm.repository.ActionStatusRepository;
import com.cw.crm.service.ActionStatusService;
import com.cw.crm.service.dto.ActionStatusDTO;
import com.cw.crm.service.mapper.ActionStatusMapper;
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
 * Test class for the ActionStatusResource REST controller.
 *
 * @see ActionStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ActionStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ActionStatusRepository actionStatusRepository;

    @Autowired
    private ActionStatusMapper actionStatusMapper;

    @Autowired
    private ActionStatusService actionStatusService;

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

    private MockMvc restActionStatusMockMvc;

    private ActionStatus actionStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionStatusResource actionStatusResource = new ActionStatusResource(actionStatusService);
        this.restActionStatusMockMvc = MockMvcBuilders.standaloneSetup(actionStatusResource)
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
    public static ActionStatus createEntity(EntityManager em) {
        ActionStatus actionStatus = new ActionStatus()
            .name(DEFAULT_NAME);
        return actionStatus;
    }

    @Before
    public void initTest() {
        actionStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionStatus() throws Exception {
        int databaseSizeBeforeCreate = actionStatusRepository.findAll().size();

        // Create the ActionStatus
        ActionStatusDTO actionStatusDTO = actionStatusMapper.toDto(actionStatus);
        restActionStatusMockMvc.perform(post("/api/action-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ActionStatus in the database
        List<ActionStatus> actionStatusList = actionStatusRepository.findAll();
        assertThat(actionStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ActionStatus testActionStatus = actionStatusList.get(actionStatusList.size() - 1);
        assertThat(testActionStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createActionStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionStatusRepository.findAll().size();

        // Create the ActionStatus with an existing ID
        actionStatus.setId(1L);
        ActionStatusDTO actionStatusDTO = actionStatusMapper.toDto(actionStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionStatusMockMvc.perform(post("/api/action-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionStatus in the database
        List<ActionStatus> actionStatusList = actionStatusRepository.findAll();
        assertThat(actionStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActionStatuses() throws Exception {
        // Initialize the database
        actionStatusRepository.saveAndFlush(actionStatus);

        // Get all the actionStatusList
        restActionStatusMockMvc.perform(get("/api/action-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getActionStatus() throws Exception {
        // Initialize the database
        actionStatusRepository.saveAndFlush(actionStatus);

        // Get the actionStatus
        restActionStatusMockMvc.perform(get("/api/action-statuses/{id}", actionStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actionStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActionStatus() throws Exception {
        // Get the actionStatus
        restActionStatusMockMvc.perform(get("/api/action-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionStatus() throws Exception {
        // Initialize the database
        actionStatusRepository.saveAndFlush(actionStatus);

        int databaseSizeBeforeUpdate = actionStatusRepository.findAll().size();

        // Update the actionStatus
        ActionStatus updatedActionStatus = actionStatusRepository.findById(actionStatus.getId()).get();
        // Disconnect from session so that the updates on updatedActionStatus are not directly saved in db
        em.detach(updatedActionStatus);
        updatedActionStatus
            .name(UPDATED_NAME);
        ActionStatusDTO actionStatusDTO = actionStatusMapper.toDto(updatedActionStatus);

        restActionStatusMockMvc.perform(put("/api/action-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionStatusDTO)))
            .andExpect(status().isOk());

        // Validate the ActionStatus in the database
        List<ActionStatus> actionStatusList = actionStatusRepository.findAll();
        assertThat(actionStatusList).hasSize(databaseSizeBeforeUpdate);
        ActionStatus testActionStatus = actionStatusList.get(actionStatusList.size() - 1);
        assertThat(testActionStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingActionStatus() throws Exception {
        int databaseSizeBeforeUpdate = actionStatusRepository.findAll().size();

        // Create the ActionStatus
        ActionStatusDTO actionStatusDTO = actionStatusMapper.toDto(actionStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionStatusMockMvc.perform(put("/api/action-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionStatus in the database
        List<ActionStatus> actionStatusList = actionStatusRepository.findAll();
        assertThat(actionStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionStatus() throws Exception {
        // Initialize the database
        actionStatusRepository.saveAndFlush(actionStatus);

        int databaseSizeBeforeDelete = actionStatusRepository.findAll().size();

        // Get the actionStatus
        restActionStatusMockMvc.perform(delete("/api/action-statuses/{id}", actionStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActionStatus> actionStatusList = actionStatusRepository.findAll();
        assertThat(actionStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionStatus.class);
        ActionStatus actionStatus1 = new ActionStatus();
        actionStatus1.setId(1L);
        ActionStatus actionStatus2 = new ActionStatus();
        actionStatus2.setId(actionStatus1.getId());
        assertThat(actionStatus1).isEqualTo(actionStatus2);
        actionStatus2.setId(2L);
        assertThat(actionStatus1).isNotEqualTo(actionStatus2);
        actionStatus1.setId(null);
        assertThat(actionStatus1).isNotEqualTo(actionStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionStatusDTO.class);
        ActionStatusDTO actionStatusDTO1 = new ActionStatusDTO();
        actionStatusDTO1.setId(1L);
        ActionStatusDTO actionStatusDTO2 = new ActionStatusDTO();
        assertThat(actionStatusDTO1).isNotEqualTo(actionStatusDTO2);
        actionStatusDTO2.setId(actionStatusDTO1.getId());
        assertThat(actionStatusDTO1).isEqualTo(actionStatusDTO2);
        actionStatusDTO2.setId(2L);
        assertThat(actionStatusDTO1).isNotEqualTo(actionStatusDTO2);
        actionStatusDTO1.setId(null);
        assertThat(actionStatusDTO1).isNotEqualTo(actionStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actionStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actionStatusMapper.fromId(null)).isNull();
    }
}
