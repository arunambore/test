package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.Invitation;
import com.beroe.live.services.usermanagement.repository.InvitationRepository;
import com.beroe.live.services.usermanagement.service.InvitationService;
import com.beroe.live.services.usermanagement.service.dto.InvitationDTO;
import com.beroe.live.services.usermanagement.service.mapper.InvitationMapper;
import com.beroe.live.services.usermanagement.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.beroe.live.services.usermanagement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvitationResource REST controller.
 *
 * @see InvitationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class InvitationResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final Long DEFAULT_PIN = 1L;
    private static final Long UPDATED_PIN = 2L;

    private static final LocalDate DEFAULT_INVITATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVITATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TILL = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_INVALID = false;
    private static final Boolean UPDATED_IS_INVALID = true;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private InvitationMapper invitationMapper;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvitationMockMvc;

    private Invitation invitation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvitationResource invitationResource = new InvitationResource(invitationService);
        this.restInvitationMockMvc = MockMvcBuilders.standaloneSetup(invitationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invitation createEntity(EntityManager em) {
        Invitation invitation = new Invitation()
            .key(DEFAULT_KEY)
            .pin(DEFAULT_PIN)
            .invitationDate(DEFAULT_INVITATION_DATE)
            .validTill(DEFAULT_VALID_TILL)
            .isInvalid(DEFAULT_IS_INVALID);
        return invitation;
    }

    @Before
    public void initTest() {
        invitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitation() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();

        // Create the Invitation
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);
        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitationDTO)))
            .andExpect(status().isCreated());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeCreate + 1);
        Invitation testInvitation = invitationList.get(invitationList.size() - 1);
        assertThat(testInvitation.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testInvitation.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testInvitation.getInvitationDate()).isEqualTo(DEFAULT_INVITATION_DATE);
        assertThat(testInvitation.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
        assertThat(testInvitation.isIsInvalid()).isEqualTo(DEFAULT_IS_INVALID);
    }

    @Test
    @Transactional
    public void createInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitationRepository.findAll().size();

        // Create the Invitation with an existing ID
        invitation.setId(1L);
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setKey(null);

        // Create the Invitation, which fails.
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitationDTO)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvitationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitationRepository.findAll().size();
        // set the field null
        invitation.setInvitationDate(null);

        // Create the Invitation, which fails.
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);

        restInvitationMockMvc.perform(post("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitationDTO)))
            .andExpect(status().isBadRequest());

        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitations() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get all the invitationList
        restInvitationMockMvc.perform(get("/api/invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN.intValue())))
            .andExpect(jsonPath("$.[*].invitationDate").value(hasItem(DEFAULT_INVITATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())))
            .andExpect(jsonPath("$.[*].isInvalid").value(hasItem(DEFAULT_IS_INVALID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(invitation.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN.intValue()))
            .andExpect(jsonPath("$.invitationDate").value(DEFAULT_INVITATION_DATE.toString()))
            .andExpect(jsonPath("$.validTill").value(DEFAULT_VALID_TILL.toString()))
            .andExpect(jsonPath("$.isInvalid").value(DEFAULT_IS_INVALID.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvitation() throws Exception {
        // Get the invitation
        restInvitationMockMvc.perform(get("/api/invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Update the invitation
        Invitation updatedInvitation = invitationRepository.findById(invitation.getId()).get();
        // Disconnect from session so that the updates on updatedInvitation are not directly saved in db
        em.detach(updatedInvitation);
        updatedInvitation
            .key(UPDATED_KEY)
            .pin(UPDATED_PIN)
            .invitationDate(UPDATED_INVITATION_DATE)
            .validTill(UPDATED_VALID_TILL)
            .isInvalid(UPDATED_IS_INVALID);
        InvitationDTO invitationDTO = invitationMapper.toDto(updatedInvitation);

        restInvitationMockMvc.perform(put("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitationDTO)))
            .andExpect(status().isOk());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeUpdate);
        Invitation testInvitation = invitationList.get(invitationList.size() - 1);
        assertThat(testInvitation.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testInvitation.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testInvitation.getInvitationDate()).isEqualTo(UPDATED_INVITATION_DATE);
        assertThat(testInvitation.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
        assertThat(testInvitation.isIsInvalid()).isEqualTo(UPDATED_IS_INVALID);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitation() throws Exception {
        int databaseSizeBeforeUpdate = invitationRepository.findAll().size();

        // Create the Invitation
        InvitationDTO invitationDTO = invitationMapper.toDto(invitation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitationMockMvc.perform(put("/api/invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(invitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invitation in the database
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        int databaseSizeBeforeDelete = invitationRepository.findAll().size();

        // Get the invitation
        restInvitationMockMvc.perform(delete("/api/invitations/{id}", invitation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Invitation> invitationList = invitationRepository.findAll();
        assertThat(invitationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invitation.class);
        Invitation invitation1 = new Invitation();
        invitation1.setId(1L);
        Invitation invitation2 = new Invitation();
        invitation2.setId(invitation1.getId());
        assertThat(invitation1).isEqualTo(invitation2);
        invitation2.setId(2L);
        assertThat(invitation1).isNotEqualTo(invitation2);
        invitation1.setId(null);
        assertThat(invitation1).isNotEqualTo(invitation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvitationDTO.class);
        InvitationDTO invitationDTO1 = new InvitationDTO();
        invitationDTO1.setId(1L);
        InvitationDTO invitationDTO2 = new InvitationDTO();
        assertThat(invitationDTO1).isNotEqualTo(invitationDTO2);
        invitationDTO2.setId(invitationDTO1.getId());
        assertThat(invitationDTO1).isEqualTo(invitationDTO2);
        invitationDTO2.setId(2L);
        assertThat(invitationDTO1).isNotEqualTo(invitationDTO2);
        invitationDTO1.setId(null);
        assertThat(invitationDTO1).isNotEqualTo(invitationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(invitationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(invitationMapper.fromId(null)).isNull();
    }
}
