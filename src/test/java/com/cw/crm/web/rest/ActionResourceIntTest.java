package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.Action;
import com.cw.crm.repository.ActionRepository;
import com.cw.crm.service.ActionService;
import com.cw.crm.service.dto.ActionDTO;
import com.cw.crm.service.mapper.ActionMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.cw.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActionResource REST controller.
 *
 * @see ActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ActionResourceIntTest {

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_START_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private ActionService actionService;

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

    private MockMvc restActionMockMvc;

    private Action action;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionResource actionResource = new ActionResource(actionService);
        this.restActionMockMvc = MockMvcBuilders.standaloneSetup(actionResource)
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
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .dueDate(DEFAULT_DUE_DATE)
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .notes(DEFAULT_NOTES);
        return action;
    }

    @Before
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action
        ActionDTO actionDTO = actionMapper.toDto(action);
        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testAction.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testAction.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testAction.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action with an existing ID
        action.setId(1L);
        ActionDTO actionDTO = actionMapper.toDto(action);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc.perform(get("/api/actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(DEFAULT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.startDateTime").value(DEFAULT_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.endDateTime").value(DEFAULT_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .dueDate(UPDATED_DUE_DATE)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .notes(UPDATED_NOTES);
        ActionDTO actionDTO = actionMapper.toDto(updatedAction);

        restActionMockMvc.perform(put("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testAction.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testAction.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testAction.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Create the Action
        ActionDTO actionDTO = actionMapper.toDto(action);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc.perform(put("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Get the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Action.class);
        Action action1 = new Action();
        action1.setId(1L);
        Action action2 = new Action();
        action2.setId(action1.getId());
        assertThat(action1).isEqualTo(action2);
        action2.setId(2L);
        assertThat(action1).isNotEqualTo(action2);
        action1.setId(null);
        assertThat(action1).isNotEqualTo(action2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionDTO.class);
        ActionDTO actionDTO1 = new ActionDTO();
        actionDTO1.setId(1L);
        ActionDTO actionDTO2 = new ActionDTO();
        assertThat(actionDTO1).isNotEqualTo(actionDTO2);
        actionDTO2.setId(actionDTO1.getId());
        assertThat(actionDTO1).isEqualTo(actionDTO2);
        actionDTO2.setId(2L);
        assertThat(actionDTO1).isNotEqualTo(actionDTO2);
        actionDTO1.setId(null);
        assertThat(actionDTO1).isNotEqualTo(actionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actionMapper.fromId(null)).isNull();
    }
}
