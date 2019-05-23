package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ActionType;
import com.cw.crm.repository.ActionTypeRepository;
import com.cw.crm.service.ActionTypeService;
import com.cw.crm.service.dto.ActionTypeDTO;
import com.cw.crm.service.mapper.ActionTypeMapper;
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
 * Test class for the ActionTypeResource REST controller.
 *
 * @see ActionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ActionTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    private ActionTypeMapper actionTypeMapper;

    @Autowired
    private ActionTypeService actionTypeService;

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

    private MockMvc restActionTypeMockMvc;

    private ActionType actionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionTypeResource actionTypeResource = new ActionTypeResource(actionTypeService);
        this.restActionTypeMockMvc = MockMvcBuilders.standaloneSetup(actionTypeResource)
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
    public static ActionType createEntity(EntityManager em) {
        ActionType actionType = new ActionType()
            .name(DEFAULT_NAME);
        return actionType;
    }

    @Before
    public void initTest() {
        actionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionType() throws Exception {
        int databaseSizeBeforeCreate = actionTypeRepository.findAll().size();

        // Create the ActionType
        ActionTypeDTO actionTypeDTO = actionTypeMapper.toDto(actionType);
        restActionTypeMockMvc.perform(post("/api/action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ActionType in the database
        List<ActionType> actionTypeList = actionTypeRepository.findAll();
        assertThat(actionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ActionType testActionType = actionTypeList.get(actionTypeList.size() - 1);
        assertThat(testActionType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createActionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionTypeRepository.findAll().size();

        // Create the ActionType with an existing ID
        actionType.setId(1L);
        ActionTypeDTO actionTypeDTO = actionTypeMapper.toDto(actionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionTypeMockMvc.perform(post("/api/action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionType in the database
        List<ActionType> actionTypeList = actionTypeRepository.findAll();
        assertThat(actionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActionTypes() throws Exception {
        // Initialize the database
        actionTypeRepository.saveAndFlush(actionType);

        // Get all the actionTypeList
        restActionTypeMockMvc.perform(get("/api/action-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getActionType() throws Exception {
        // Initialize the database
        actionTypeRepository.saveAndFlush(actionType);

        // Get the actionType
        restActionTypeMockMvc.perform(get("/api/action-types/{id}", actionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActionType() throws Exception {
        // Get the actionType
        restActionTypeMockMvc.perform(get("/api/action-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionType() throws Exception {
        // Initialize the database
        actionTypeRepository.saveAndFlush(actionType);

        int databaseSizeBeforeUpdate = actionTypeRepository.findAll().size();

        // Update the actionType
        ActionType updatedActionType = actionTypeRepository.findById(actionType.getId()).get();
        // Disconnect from session so that the updates on updatedActionType are not directly saved in db
        em.detach(updatedActionType);
        updatedActionType
            .name(UPDATED_NAME);
        ActionTypeDTO actionTypeDTO = actionTypeMapper.toDto(updatedActionType);

        restActionTypeMockMvc.perform(put("/api/action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ActionType in the database
        List<ActionType> actionTypeList = actionTypeRepository.findAll();
        assertThat(actionTypeList).hasSize(databaseSizeBeforeUpdate);
        ActionType testActionType = actionTypeList.get(actionTypeList.size() - 1);
        assertThat(testActionType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingActionType() throws Exception {
        int databaseSizeBeforeUpdate = actionTypeRepository.findAll().size();

        // Create the ActionType
        ActionTypeDTO actionTypeDTO = actionTypeMapper.toDto(actionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionTypeMockMvc.perform(put("/api/action-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionType in the database
        List<ActionType> actionTypeList = actionTypeRepository.findAll();
        assertThat(actionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionType() throws Exception {
        // Initialize the database
        actionTypeRepository.saveAndFlush(actionType);

        int databaseSizeBeforeDelete = actionTypeRepository.findAll().size();

        // Get the actionType
        restActionTypeMockMvc.perform(delete("/api/action-types/{id}", actionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActionType> actionTypeList = actionTypeRepository.findAll();
        assertThat(actionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionType.class);
        ActionType actionType1 = new ActionType();
        actionType1.setId(1L);
        ActionType actionType2 = new ActionType();
        actionType2.setId(actionType1.getId());
        assertThat(actionType1).isEqualTo(actionType2);
        actionType2.setId(2L);
        assertThat(actionType1).isNotEqualTo(actionType2);
        actionType1.setId(null);
        assertThat(actionType1).isNotEqualTo(actionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionTypeDTO.class);
        ActionTypeDTO actionTypeDTO1 = new ActionTypeDTO();
        actionTypeDTO1.setId(1L);
        ActionTypeDTO actionTypeDTO2 = new ActionTypeDTO();
        assertThat(actionTypeDTO1).isNotEqualTo(actionTypeDTO2);
        actionTypeDTO2.setId(actionTypeDTO1.getId());
        assertThat(actionTypeDTO1).isEqualTo(actionTypeDTO2);
        actionTypeDTO2.setId(2L);
        assertThat(actionTypeDTO1).isNotEqualTo(actionTypeDTO2);
        actionTypeDTO1.setId(null);
        assertThat(actionTypeDTO1).isNotEqualTo(actionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actionTypeMapper.fromId(null)).isNull();
    }
}
