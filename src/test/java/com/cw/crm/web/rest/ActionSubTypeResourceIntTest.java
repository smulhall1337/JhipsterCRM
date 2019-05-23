package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.ActionSubType;
import com.cw.crm.repository.ActionSubTypeRepository;
import com.cw.crm.service.ActionSubTypeService;
import com.cw.crm.service.dto.ActionSubTypeDTO;
import com.cw.crm.service.mapper.ActionSubTypeMapper;
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
 * Test class for the ActionSubTypeResource REST controller.
 *
 * @see ActionSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ActionSubTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ActionSubTypeRepository actionSubTypeRepository;

    @Autowired
    private ActionSubTypeMapper actionSubTypeMapper;

    @Autowired
    private ActionSubTypeService actionSubTypeService;

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

    private MockMvc restActionSubTypeMockMvc;

    private ActionSubType actionSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionSubTypeResource actionSubTypeResource = new ActionSubTypeResource(actionSubTypeService);
        this.restActionSubTypeMockMvc = MockMvcBuilders.standaloneSetup(actionSubTypeResource)
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
    public static ActionSubType createEntity(EntityManager em) {
        ActionSubType actionSubType = new ActionSubType()
            .name(DEFAULT_NAME);
        return actionSubType;
    }

    @Before
    public void initTest() {
        actionSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionSubType() throws Exception {
        int databaseSizeBeforeCreate = actionSubTypeRepository.findAll().size();

        // Create the ActionSubType
        ActionSubTypeDTO actionSubTypeDTO = actionSubTypeMapper.toDto(actionSubType);
        restActionSubTypeMockMvc.perform(post("/api/action-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionSubTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ActionSubType in the database
        List<ActionSubType> actionSubTypeList = actionSubTypeRepository.findAll();
        assertThat(actionSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ActionSubType testActionSubType = actionSubTypeList.get(actionSubTypeList.size() - 1);
        assertThat(testActionSubType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createActionSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionSubTypeRepository.findAll().size();

        // Create the ActionSubType with an existing ID
        actionSubType.setId(1L);
        ActionSubTypeDTO actionSubTypeDTO = actionSubTypeMapper.toDto(actionSubType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionSubTypeMockMvc.perform(post("/api/action-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionSubTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionSubType in the database
        List<ActionSubType> actionSubTypeList = actionSubTypeRepository.findAll();
        assertThat(actionSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActionSubTypes() throws Exception {
        // Initialize the database
        actionSubTypeRepository.saveAndFlush(actionSubType);

        // Get all the actionSubTypeList
        restActionSubTypeMockMvc.perform(get("/api/action-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getActionSubType() throws Exception {
        // Initialize the database
        actionSubTypeRepository.saveAndFlush(actionSubType);

        // Get the actionSubType
        restActionSubTypeMockMvc.perform(get("/api/action-sub-types/{id}", actionSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actionSubType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActionSubType() throws Exception {
        // Get the actionSubType
        restActionSubTypeMockMvc.perform(get("/api/action-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionSubType() throws Exception {
        // Initialize the database
        actionSubTypeRepository.saveAndFlush(actionSubType);

        int databaseSizeBeforeUpdate = actionSubTypeRepository.findAll().size();

        // Update the actionSubType
        ActionSubType updatedActionSubType = actionSubTypeRepository.findById(actionSubType.getId()).get();
        // Disconnect from session so that the updates on updatedActionSubType are not directly saved in db
        em.detach(updatedActionSubType);
        updatedActionSubType
            .name(UPDATED_NAME);
        ActionSubTypeDTO actionSubTypeDTO = actionSubTypeMapper.toDto(updatedActionSubType);

        restActionSubTypeMockMvc.perform(put("/api/action-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionSubTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ActionSubType in the database
        List<ActionSubType> actionSubTypeList = actionSubTypeRepository.findAll();
        assertThat(actionSubTypeList).hasSize(databaseSizeBeforeUpdate);
        ActionSubType testActionSubType = actionSubTypeList.get(actionSubTypeList.size() - 1);
        assertThat(testActionSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingActionSubType() throws Exception {
        int databaseSizeBeforeUpdate = actionSubTypeRepository.findAll().size();

        // Create the ActionSubType
        ActionSubTypeDTO actionSubTypeDTO = actionSubTypeMapper.toDto(actionSubType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionSubTypeMockMvc.perform(put("/api/action-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actionSubTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActionSubType in the database
        List<ActionSubType> actionSubTypeList = actionSubTypeRepository.findAll();
        assertThat(actionSubTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionSubType() throws Exception {
        // Initialize the database
        actionSubTypeRepository.saveAndFlush(actionSubType);

        int databaseSizeBeforeDelete = actionSubTypeRepository.findAll().size();

        // Get the actionSubType
        restActionSubTypeMockMvc.perform(delete("/api/action-sub-types/{id}", actionSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActionSubType> actionSubTypeList = actionSubTypeRepository.findAll();
        assertThat(actionSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionSubType.class);
        ActionSubType actionSubType1 = new ActionSubType();
        actionSubType1.setId(1L);
        ActionSubType actionSubType2 = new ActionSubType();
        actionSubType2.setId(actionSubType1.getId());
        assertThat(actionSubType1).isEqualTo(actionSubType2);
        actionSubType2.setId(2L);
        assertThat(actionSubType1).isNotEqualTo(actionSubType2);
        actionSubType1.setId(null);
        assertThat(actionSubType1).isNotEqualTo(actionSubType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionSubTypeDTO.class);
        ActionSubTypeDTO actionSubTypeDTO1 = new ActionSubTypeDTO();
        actionSubTypeDTO1.setId(1L);
        ActionSubTypeDTO actionSubTypeDTO2 = new ActionSubTypeDTO();
        assertThat(actionSubTypeDTO1).isNotEqualTo(actionSubTypeDTO2);
        actionSubTypeDTO2.setId(actionSubTypeDTO1.getId());
        assertThat(actionSubTypeDTO1).isEqualTo(actionSubTypeDTO2);
        actionSubTypeDTO2.setId(2L);
        assertThat(actionSubTypeDTO1).isNotEqualTo(actionSubTypeDTO2);
        actionSubTypeDTO1.setId(null);
        assertThat(actionSubTypeDTO1).isNotEqualTo(actionSubTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(actionSubTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(actionSubTypeMapper.fromId(null)).isNull();
    }
}
