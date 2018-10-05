package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.UserState;
import com.beroe.live.services.usermanagement.repository.UserStateRepository;
import com.beroe.live.services.usermanagement.service.UserStateService;
import com.beroe.live.services.usermanagement.service.dto.UserStateDTO;
import com.beroe.live.services.usermanagement.service.mapper.UserStateMapper;
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
 * Test class for the UserStateResource REST controller.
 *
 * @see UserStateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class UserStateResourceIntTest {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserStateRepository userStateRepository;

    @Autowired
    private UserStateMapper userStateMapper;
    
    @Autowired
    private UserStateService userStateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserStateMockMvc;

    private UserState userState;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserStateResource userStateResource = new UserStateResource(userStateService);
        this.restUserStateMockMvc = MockMvcBuilders.standaloneSetup(userStateResource)
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
    public static UserState createEntity(EntityManager em) {
        UserState userState = new UserState()
            .state(DEFAULT_STATE)
            .date(DEFAULT_DATE);
        return userState;
    }

    @Before
    public void initTest() {
        userState = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserState() throws Exception {
        int databaseSizeBeforeCreate = userStateRepository.findAll().size();

        // Create the UserState
        UserStateDTO userStateDTO = userStateMapper.toDto(userState);
        restUserStateMockMvc.perform(post("/api/user-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStateDTO)))
            .andExpect(status().isCreated());

        // Validate the UserState in the database
        List<UserState> userStateList = userStateRepository.findAll();
        assertThat(userStateList).hasSize(databaseSizeBeforeCreate + 1);
        UserState testUserState = userStateList.get(userStateList.size() - 1);
        assertThat(testUserState.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUserState.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createUserStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userStateRepository.findAll().size();

        // Create the UserState with an existing ID
        userState.setId(1L);
        UserStateDTO userStateDTO = userStateMapper.toDto(userState);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserStateMockMvc.perform(post("/api/user-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserState in the database
        List<UserState> userStateList = userStateRepository.findAll();
        assertThat(userStateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userStateRepository.findAll().size();
        // set the field null
        userState.setState(null);

        // Create the UserState, which fails.
        UserStateDTO userStateDTO = userStateMapper.toDto(userState);

        restUserStateMockMvc.perform(post("/api/user-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStateDTO)))
            .andExpect(status().isBadRequest());

        List<UserState> userStateList = userStateRepository.findAll();
        assertThat(userStateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserStates() throws Exception {
        // Initialize the database
        userStateRepository.saveAndFlush(userState);

        // Get all the userStateList
        restUserStateMockMvc.perform(get("/api/user-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userState.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserState() throws Exception {
        // Initialize the database
        userStateRepository.saveAndFlush(userState);

        // Get the userState
        restUserStateMockMvc.perform(get("/api/user-states/{id}", userState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userState.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserState() throws Exception {
        // Get the userState
        restUserStateMockMvc.perform(get("/api/user-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserState() throws Exception {
        // Initialize the database
        userStateRepository.saveAndFlush(userState);

        int databaseSizeBeforeUpdate = userStateRepository.findAll().size();

        // Update the userState
        UserState updatedUserState = userStateRepository.findById(userState.getId()).get();
        // Disconnect from session so that the updates on updatedUserState are not directly saved in db
        em.detach(updatedUserState);
        updatedUserState
            .state(UPDATED_STATE)
            .date(UPDATED_DATE);
        UserStateDTO userStateDTO = userStateMapper.toDto(updatedUserState);

        restUserStateMockMvc.perform(put("/api/user-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStateDTO)))
            .andExpect(status().isOk());

        // Validate the UserState in the database
        List<UserState> userStateList = userStateRepository.findAll();
        assertThat(userStateList).hasSize(databaseSizeBeforeUpdate);
        UserState testUserState = userStateList.get(userStateList.size() - 1);
        assertThat(testUserState.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUserState.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserState() throws Exception {
        int databaseSizeBeforeUpdate = userStateRepository.findAll().size();

        // Create the UserState
        UserStateDTO userStateDTO = userStateMapper.toDto(userState);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserStateMockMvc.perform(put("/api/user-states")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserState in the database
        List<UserState> userStateList = userStateRepository.findAll();
        assertThat(userStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserState() throws Exception {
        // Initialize the database
        userStateRepository.saveAndFlush(userState);

        int databaseSizeBeforeDelete = userStateRepository.findAll().size();

        // Get the userState
        restUserStateMockMvc.perform(delete("/api/user-states/{id}", userState.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserState> userStateList = userStateRepository.findAll();
        assertThat(userStateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserState.class);
        UserState userState1 = new UserState();
        userState1.setId(1L);
        UserState userState2 = new UserState();
        userState2.setId(userState1.getId());
        assertThat(userState1).isEqualTo(userState2);
        userState2.setId(2L);
        assertThat(userState1).isNotEqualTo(userState2);
        userState1.setId(null);
        assertThat(userState1).isNotEqualTo(userState2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserStateDTO.class);
        UserStateDTO userStateDTO1 = new UserStateDTO();
        userStateDTO1.setId(1L);
        UserStateDTO userStateDTO2 = new UserStateDTO();
        assertThat(userStateDTO1).isNotEqualTo(userStateDTO2);
        userStateDTO2.setId(userStateDTO1.getId());
        assertThat(userStateDTO1).isEqualTo(userStateDTO2);
        userStateDTO2.setId(2L);
        assertThat(userStateDTO1).isNotEqualTo(userStateDTO2);
        userStateDTO1.setId(null);
        assertThat(userStateDTO1).isNotEqualTo(userStateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userStateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userStateMapper.fromId(null)).isNull();
    }
}
