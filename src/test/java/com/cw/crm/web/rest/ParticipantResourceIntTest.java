package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.Participant;
import com.cw.crm.repository.ParticipantRepository;
import com.cw.crm.service.ParticipantService;
import com.cw.crm.service.dto.ParticipantDTO;
import com.cw.crm.service.mapper.ParticipantMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.cw.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ParticipantResource REST controller.
 *
 * @see ParticipantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class ParticipantResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_INITIAL = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_INITIAL = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PRIMARY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_PHONE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_PHONE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_PHONE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_PHONE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICARE_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MEDICARE_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAID_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAID_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final Integer DEFAULT_PARTICIPANT_STATUS = 1;
    private static final Integer UPDATED_PARTICIPANT_STATUS = 2;

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_AUTHORIZED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AUTHORIZED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AUTHORIZATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORIZATION_NUMBER = "BBBBBBBBBB";

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantMapper participantMapper;

    @Autowired
    private ParticipantService participantService;

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

    private MockMvc restParticipantMockMvc;

    private Participant participant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParticipantResource participantResource = new ParticipantResource(participantService);
        this.restParticipantMockMvc = MockMvcBuilders.standaloneSetup(participantResource)
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
    public static Participant createEntity(EntityManager em) {
        Participant participant = new Participant()
            .firstName(DEFAULT_FIRST_NAME)
            .middleInitial(DEFAULT_MIDDLE_INITIAL)
            .lastName(DEFAULT_LAST_NAME)
            .title(DEFAULT_TITLE)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .dob(DEFAULT_DOB)
            .primaryPhone(DEFAULT_PRIMARY_PHONE)
            .primaryPhoneType(DEFAULT_PRIMARY_PHONE_TYPE)
            .secondaryPhone(DEFAULT_SECONDARY_PHONE)
            .secondaryPhoneType(DEFAULT_SECONDARY_PHONE_TYPE)
            .email(DEFAULT_EMAIL)
            .zip(DEFAULT_ZIP)
            .medicareIdNumber(DEFAULT_MEDICARE_ID_NUMBER)
            .medicaidIdNumber(DEFAULT_MEDICAID_ID_NUMBER)
            .gender(DEFAULT_GENDER)
            .participantStatus(DEFAULT_PARTICIPANT_STATUS)
            .county(DEFAULT_COUNTY)
            .dateAuthorized(DEFAULT_DATE_AUTHORIZED)
            .authorizationNumber(DEFAULT_AUTHORIZATION_NUMBER);
        return participant;
    }

    @Before
    public void initTest() {
        participant = createEntity(em);
    }

    @Test
    @Transactional
    public void createParticipant() throws Exception {
        int databaseSizeBeforeCreate = participantRepository.findAll().size();

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);
        restParticipantMockMvc.perform(post("/api/participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantDTO)))
            .andExpect(status().isCreated());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeCreate + 1);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testParticipant.getMiddleInitial()).isEqualTo(DEFAULT_MIDDLE_INITIAL);
        assertThat(testParticipant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testParticipant.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testParticipant.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testParticipant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testParticipant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testParticipant.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testParticipant.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testParticipant.getPrimaryPhone()).isEqualTo(DEFAULT_PRIMARY_PHONE);
        assertThat(testParticipant.getPrimaryPhoneType()).isEqualTo(DEFAULT_PRIMARY_PHONE_TYPE);
        assertThat(testParticipant.getSecondaryPhone()).isEqualTo(DEFAULT_SECONDARY_PHONE);
        assertThat(testParticipant.getSecondaryPhoneType()).isEqualTo(DEFAULT_SECONDARY_PHONE_TYPE);
        assertThat(testParticipant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParticipant.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testParticipant.getMedicareIdNumber()).isEqualTo(DEFAULT_MEDICARE_ID_NUMBER);
        assertThat(testParticipant.getMedicaidIdNumber()).isEqualTo(DEFAULT_MEDICAID_ID_NUMBER);
        assertThat(testParticipant.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testParticipant.getParticipantStatus()).isEqualTo(DEFAULT_PARTICIPANT_STATUS);
        assertThat(testParticipant.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testParticipant.getDateAuthorized()).isEqualTo(DEFAULT_DATE_AUTHORIZED);
        assertThat(testParticipant.getAuthorizationNumber()).isEqualTo(DEFAULT_AUTHORIZATION_NUMBER);
    }

    @Test
    @Transactional
    public void createParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = participantRepository.findAll().size();

        // Create the Participant with an existing ID
        participant.setId(1L);
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantMockMvc.perform(post("/api/participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = participantRepository.findAll().size();
        // set the field null
        participant.setFirstName(null);

        // Create the Participant, which fails.
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        restParticipantMockMvc.perform(post("/api/participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantDTO)))
            .andExpect(status().isBadRequest());

        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = participantRepository.findAll().size();
        // set the field null
        participant.setLastName(null);

        // Create the Participant, which fails.
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        restParticipantMockMvc.perform(post("/api/participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantDTO)))
            .andExpect(status().isBadRequest());

        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParticipants() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList
        restParticipantMockMvc.perform(get("/api/participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleInitial").value(hasItem(DEFAULT_MIDDLE_INITIAL.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].primaryPhone").value(hasItem(DEFAULT_PRIMARY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].primaryPhoneType").value(hasItem(DEFAULT_PRIMARY_PHONE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].secondaryPhone").value(hasItem(DEFAULT_SECONDARY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].secondaryPhoneType").value(hasItem(DEFAULT_SECONDARY_PHONE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].medicareIdNumber").value(hasItem(DEFAULT_MEDICARE_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].medicaidIdNumber").value(hasItem(DEFAULT_MEDICAID_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].participantStatus").value(hasItem(DEFAULT_PARTICIPANT_STATUS)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY.toString())))
            .andExpect(jsonPath("$.[*].dateAuthorized").value(hasItem(DEFAULT_DATE_AUTHORIZED.toString())))
            .andExpect(jsonPath("$.[*].authorizationNumber").value(hasItem(DEFAULT_AUTHORIZATION_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get the participant
        restParticipantMockMvc.perform(get("/api/participants/{id}", participant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participant.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleInitial").value(DEFAULT_MIDDLE_INITIAL.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.primaryPhone").value(DEFAULT_PRIMARY_PHONE.toString()))
            .andExpect(jsonPath("$.primaryPhoneType").value(DEFAULT_PRIMARY_PHONE_TYPE.toString()))
            .andExpect(jsonPath("$.secondaryPhone").value(DEFAULT_SECONDARY_PHONE.toString()))
            .andExpect(jsonPath("$.secondaryPhoneType").value(DEFAULT_SECONDARY_PHONE_TYPE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.medicareIdNumber").value(DEFAULT_MEDICARE_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.medicaidIdNumber").value(DEFAULT_MEDICAID_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.participantStatus").value(DEFAULT_PARTICIPANT_STATUS))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY.toString()))
            .andExpect(jsonPath("$.dateAuthorized").value(DEFAULT_DATE_AUTHORIZED.toString()))
            .andExpect(jsonPath("$.authorizationNumber").value(DEFAULT_AUTHORIZATION_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParticipant() throws Exception {
        // Get the participant
        restParticipantMockMvc.perform(get("/api/participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant
        Participant updatedParticipant = participantRepository.findById(participant.getId()).get();
        // Disconnect from session so that the updates on updatedParticipant are not directly saved in db
        em.detach(updatedParticipant);
        updatedParticipant
            .firstName(UPDATED_FIRST_NAME)
            .middleInitial(UPDATED_MIDDLE_INITIAL)
            .lastName(UPDATED_LAST_NAME)
            .title(UPDATED_TITLE)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .dob(UPDATED_DOB)
            .primaryPhone(UPDATED_PRIMARY_PHONE)
            .primaryPhoneType(UPDATED_PRIMARY_PHONE_TYPE)
            .secondaryPhone(UPDATED_SECONDARY_PHONE)
            .secondaryPhoneType(UPDATED_SECONDARY_PHONE_TYPE)
            .email(UPDATED_EMAIL)
            .zip(UPDATED_ZIP)
            .medicareIdNumber(UPDATED_MEDICARE_ID_NUMBER)
            .medicaidIdNumber(UPDATED_MEDICAID_ID_NUMBER)
            .gender(UPDATED_GENDER)
            .participantStatus(UPDATED_PARTICIPANT_STATUS)
            .county(UPDATED_COUNTY)
            .dateAuthorized(UPDATED_DATE_AUTHORIZED)
            .authorizationNumber(UPDATED_AUTHORIZATION_NUMBER);
        ParticipantDTO participantDTO = participantMapper.toDto(updatedParticipant);

        restParticipantMockMvc.perform(put("/api/participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantDTO)))
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testParticipant.getMiddleInitial()).isEqualTo(UPDATED_MIDDLE_INITIAL);
        assertThat(testParticipant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testParticipant.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testParticipant.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testParticipant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testParticipant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testParticipant.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testParticipant.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testParticipant.getPrimaryPhone()).isEqualTo(UPDATED_PRIMARY_PHONE);
        assertThat(testParticipant.getPrimaryPhoneType()).isEqualTo(UPDATED_PRIMARY_PHONE_TYPE);
        assertThat(testParticipant.getSecondaryPhone()).isEqualTo(UPDATED_SECONDARY_PHONE);
        assertThat(testParticipant.getSecondaryPhoneType()).isEqualTo(UPDATED_SECONDARY_PHONE_TYPE);
        assertThat(testParticipant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParticipant.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testParticipant.getMedicareIdNumber()).isEqualTo(UPDATED_MEDICARE_ID_NUMBER);
        assertThat(testParticipant.getMedicaidIdNumber()).isEqualTo(UPDATED_MEDICAID_ID_NUMBER);
        assertThat(testParticipant.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testParticipant.getParticipantStatus()).isEqualTo(UPDATED_PARTICIPANT_STATUS);
        assertThat(testParticipant.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testParticipant.getDateAuthorized()).isEqualTo(UPDATED_DATE_AUTHORIZED);
        assertThat(testParticipant.getAuthorizationNumber()).isEqualTo(UPDATED_AUTHORIZATION_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Create the Participant
        ParticipantDTO participantDTO = participantMapper.toDto(participant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc.perform(put("/api/participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeDelete = participantRepository.findAll().size();

        // Delete the participant
        restParticipantMockMvc.perform(delete("/api/participants/{id}", participant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participant.class);
        Participant participant1 = new Participant();
        participant1.setId(1L);
        Participant participant2 = new Participant();
        participant2.setId(participant1.getId());
        assertThat(participant1).isEqualTo(participant2);
        participant2.setId(2L);
        assertThat(participant1).isNotEqualTo(participant2);
        participant1.setId(null);
        assertThat(participant1).isNotEqualTo(participant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipantDTO.class);
        ParticipantDTO participantDTO1 = new ParticipantDTO();
        participantDTO1.setId(1L);
        ParticipantDTO participantDTO2 = new ParticipantDTO();
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
        participantDTO2.setId(participantDTO1.getId());
        assertThat(participantDTO1).isEqualTo(participantDTO2);
        participantDTO2.setId(2L);
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
        participantDTO1.setId(null);
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(participantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(participantMapper.fromId(null)).isNull();
    }
}
