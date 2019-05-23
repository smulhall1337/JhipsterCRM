package com.cw.crm.web.rest;

import com.cw.crm.Cwcrm2App;

import com.cw.crm.domain.AltContact;
import com.cw.crm.repository.AltContactRepository;
import com.cw.crm.service.AltContactService;
import com.cw.crm.service.dto.AltContactDTO;
import com.cw.crm.service.mapper.AltContactMapper;
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
import java.util.List;


import static com.cw.crm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AltContactResource REST controller.
 *
 * @see AltContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cwcrm2App.class)
public class AltContactResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private AltContactRepository altContactRepository;

    @Autowired
    private AltContactMapper altContactMapper;

    @Autowired
    private AltContactService altContactService;

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

    private MockMvc restAltContactMockMvc;

    private AltContact altContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AltContactResource altContactResource = new AltContactResource(altContactService);
        this.restAltContactMockMvc = MockMvcBuilders.standaloneSetup(altContactResource)
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
    public static AltContact createEntity(EntityManager em) {
        AltContact altContact = new AltContact()
            .name(DEFAULT_NAME)
            .relationship(DEFAULT_RELATIONSHIP)
            .phone(DEFAULT_PHONE)
            .notes(DEFAULT_NOTES);
        return altContact;
    }

    @Before
    public void initTest() {
        altContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createAltContact() throws Exception {
        int databaseSizeBeforeCreate = altContactRepository.findAll().size();

        // Create the AltContact
        AltContactDTO altContactDTO = altContactMapper.toDto(altContact);
        restAltContactMockMvc.perform(post("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isCreated());

        // Validate the AltContact in the database
        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeCreate + 1);
        AltContact testAltContact = altContactList.get(altContactList.size() - 1);
        assertThat(testAltContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAltContact.getRelationship()).isEqualTo(DEFAULT_RELATIONSHIP);
        assertThat(testAltContact.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAltContact.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createAltContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = altContactRepository.findAll().size();

        // Create the AltContact with an existing ID
        altContact.setId(1L);
        AltContactDTO altContactDTO = altContactMapper.toDto(altContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAltContactMockMvc.perform(post("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AltContact in the database
        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = altContactRepository.findAll().size();
        // set the field null
        altContact.setName(null);

        // Create the AltContact, which fails.
        AltContactDTO altContactDTO = altContactMapper.toDto(altContact);

        restAltContactMockMvc.perform(post("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isBadRequest());

        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationshipIsRequired() throws Exception {
        int databaseSizeBeforeTest = altContactRepository.findAll().size();
        // set the field null
        altContact.setRelationship(null);

        // Create the AltContact, which fails.
        AltContactDTO altContactDTO = altContactMapper.toDto(altContact);

        restAltContactMockMvc.perform(post("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isBadRequest());

        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = altContactRepository.findAll().size();
        // set the field null
        altContact.setPhone(null);

        // Create the AltContact, which fails.
        AltContactDTO altContactDTO = altContactMapper.toDto(altContact);

        restAltContactMockMvc.perform(post("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isBadRequest());

        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAltContacts() throws Exception {
        // Initialize the database
        altContactRepository.saveAndFlush(altContact);

        // Get all the altContactList
        restAltContactMockMvc.perform(get("/api/alt-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(altContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].relationship").value(hasItem(DEFAULT_RELATIONSHIP.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getAltContact() throws Exception {
        // Initialize the database
        altContactRepository.saveAndFlush(altContact);

        // Get the altContact
        restAltContactMockMvc.perform(get("/api/alt-contacts/{id}", altContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(altContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.relationship").value(DEFAULT_RELATIONSHIP.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAltContact() throws Exception {
        // Get the altContact
        restAltContactMockMvc.perform(get("/api/alt-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAltContact() throws Exception {
        // Initialize the database
        altContactRepository.saveAndFlush(altContact);

        int databaseSizeBeforeUpdate = altContactRepository.findAll().size();

        // Update the altContact
        AltContact updatedAltContact = altContactRepository.findById(altContact.getId()).get();
        // Disconnect from session so that the updates on updatedAltContact are not directly saved in db
        em.detach(updatedAltContact);
        updatedAltContact
            .name(UPDATED_NAME)
            .relationship(UPDATED_RELATIONSHIP)
            .phone(UPDATED_PHONE)
            .notes(UPDATED_NOTES);
        AltContactDTO altContactDTO = altContactMapper.toDto(updatedAltContact);

        restAltContactMockMvc.perform(put("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isOk());

        // Validate the AltContact in the database
        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeUpdate);
        AltContact testAltContact = altContactList.get(altContactList.size() - 1);
        assertThat(testAltContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAltContact.getRelationship()).isEqualTo(UPDATED_RELATIONSHIP);
        assertThat(testAltContact.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAltContact.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingAltContact() throws Exception {
        int databaseSizeBeforeUpdate = altContactRepository.findAll().size();

        // Create the AltContact
        AltContactDTO altContactDTO = altContactMapper.toDto(altContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAltContactMockMvc.perform(put("/api/alt-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(altContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AltContact in the database
        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAltContact() throws Exception {
        // Initialize the database
        altContactRepository.saveAndFlush(altContact);

        int databaseSizeBeforeDelete = altContactRepository.findAll().size();

        // Delete the altContact
        restAltContactMockMvc.perform(delete("/api/alt-contacts/{id}", altContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AltContact> altContactList = altContactRepository.findAll();
        assertThat(altContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AltContact.class);
        AltContact altContact1 = new AltContact();
        altContact1.setId(1L);
        AltContact altContact2 = new AltContact();
        altContact2.setId(altContact1.getId());
        assertThat(altContact1).isEqualTo(altContact2);
        altContact2.setId(2L);
        assertThat(altContact1).isNotEqualTo(altContact2);
        altContact1.setId(null);
        assertThat(altContact1).isNotEqualTo(altContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AltContactDTO.class);
        AltContactDTO altContactDTO1 = new AltContactDTO();
        altContactDTO1.setId(1L);
        AltContactDTO altContactDTO2 = new AltContactDTO();
        assertThat(altContactDTO1).isNotEqualTo(altContactDTO2);
        altContactDTO2.setId(altContactDTO1.getId());
        assertThat(altContactDTO1).isEqualTo(altContactDTO2);
        altContactDTO2.setId(2L);
        assertThat(altContactDTO1).isNotEqualTo(altContactDTO2);
        altContactDTO1.setId(null);
        assertThat(altContactDTO1).isNotEqualTo(altContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(altContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(altContactMapper.fromId(null)).isNull();
    }
}
