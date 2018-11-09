package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.SystemConfiguration;
import com.beroe.live.services.usermanagement.repository.SystemConfigurationRepository;
import com.beroe.live.services.usermanagement.service.SystemConfigurationService;
import com.beroe.live.services.usermanagement.service.dto.SystemConfigurationDTO;
import com.beroe.live.services.usermanagement.service.mapper.SystemConfigurationMapper;
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
import java.util.List;


import static com.beroe.live.services.usermanagement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SystemConfigurationResource REST controller.
 *
 * @see SystemConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class SystemConfigurationResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SystemConfigurationRepository systemConfigurationRepository;

    @Autowired
    private SystemConfigurationMapper systemConfigurationMapper;

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemConfigurationMockMvc;

    private SystemConfiguration systemConfiguration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemConfigurationResource systemConfigurationResource = new SystemConfigurationResource(systemConfigurationService);
        this.restSystemConfigurationMockMvc = MockMvcBuilders.standaloneSetup(systemConfigurationResource)
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
    public static SystemConfiguration createEntity(EntityManager em) {
        SystemConfiguration systemConfiguration = new SystemConfiguration()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return systemConfiguration;
    }

    @Before
    public void initTest() {
        systemConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemConfiguration() throws Exception {
        int databaseSizeBeforeCreate = systemConfigurationRepository.findAll().size();

        // Create the SystemConfiguration
        SystemConfigurationDTO systemConfigurationDTO = systemConfigurationMapper.toDto(systemConfiguration);
        restSystemConfigurationMockMvc.perform(post("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        SystemConfiguration testSystemConfiguration = systemConfigurationList.get(systemConfigurationList.size() - 1);
        assertThat(testSystemConfiguration.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSystemConfiguration.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSystemConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemConfigurationRepository.findAll().size();

        // Create the SystemConfiguration with an existing ID
        systemConfiguration.setId(1L);
        SystemConfigurationDTO systemConfigurationDTO = systemConfigurationMapper.toDto(systemConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemConfigurationMockMvc.perform(post("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemConfigurationRepository.findAll().size();
        // set the field null
        systemConfiguration.setKey(null);

        // Create the SystemConfiguration, which fails.
        SystemConfigurationDTO systemConfigurationDTO = systemConfigurationMapper.toDto(systemConfiguration);

        restSystemConfigurationMockMvc.perform(post("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemConfigurationRepository.findAll().size();
        // set the field null
        systemConfiguration.setValue(null);

        // Create the SystemConfiguration, which fails.
        SystemConfigurationDTO systemConfigurationDTO = systemConfigurationMapper.toDto(systemConfiguration);

        restSystemConfigurationMockMvc.perform(post("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemConfigurations() throws Exception {
        // Initialize the database
        systemConfigurationRepository.saveAndFlush(systemConfiguration);

        // Get all the systemConfigurationList
        restSystemConfigurationMockMvc.perform(get("/api/system-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationRepository.saveAndFlush(systemConfiguration);

        // Get the systemConfiguration
        restSystemConfigurationMockMvc.perform(get("/api/system-configurations/{id}", systemConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystemConfiguration() throws Exception {
        // Get the systemConfiguration
        restSystemConfigurationMockMvc.perform(get("/api/system-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationRepository.saveAndFlush(systemConfiguration);

        int databaseSizeBeforeUpdate = systemConfigurationRepository.findAll().size();

        // Update the systemConfiguration
        SystemConfiguration updatedSystemConfiguration = systemConfigurationRepository.findById(systemConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedSystemConfiguration are not directly saved in db
        em.detach(updatedSystemConfiguration);
        updatedSystemConfiguration
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        SystemConfigurationDTO systemConfigurationDTO = systemConfigurationMapper.toDto(updatedSystemConfiguration);

        restSystemConfigurationMockMvc.perform(put("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeUpdate);
        SystemConfiguration testSystemConfiguration = systemConfigurationList.get(systemConfigurationList.size() - 1);
        assertThat(testSystemConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSystemConfiguration.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = systemConfigurationRepository.findAll().size();

        // Create the SystemConfiguration
        SystemConfigurationDTO systemConfigurationDTO = systemConfigurationMapper.toDto(systemConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemConfigurationMockMvc.perform(put("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationRepository.saveAndFlush(systemConfiguration);

        int databaseSizeBeforeDelete = systemConfigurationRepository.findAll().size();

        // Get the systemConfiguration
        restSystemConfigurationMockMvc.perform(delete("/api/system-configurations/{id}", systemConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemConfiguration.class);
        SystemConfiguration systemConfiguration1 = new SystemConfiguration();
        systemConfiguration1.setId(1L);
        SystemConfiguration systemConfiguration2 = new SystemConfiguration();
        systemConfiguration2.setId(systemConfiguration1.getId());
        assertThat(systemConfiguration1).isEqualTo(systemConfiguration2);
        systemConfiguration2.setId(2L);
        assertThat(systemConfiguration1).isNotEqualTo(systemConfiguration2);
        systemConfiguration1.setId(null);
        assertThat(systemConfiguration1).isNotEqualTo(systemConfiguration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemConfigurationDTO.class);
        SystemConfigurationDTO systemConfigurationDTO1 = new SystemConfigurationDTO();
        systemConfigurationDTO1.setId(1L);
        SystemConfigurationDTO systemConfigurationDTO2 = new SystemConfigurationDTO();
        assertThat(systemConfigurationDTO1).isNotEqualTo(systemConfigurationDTO2);
        systemConfigurationDTO2.setId(systemConfigurationDTO1.getId());
        assertThat(systemConfigurationDTO1).isEqualTo(systemConfigurationDTO2);
        systemConfigurationDTO2.setId(2L);
        assertThat(systemConfigurationDTO1).isNotEqualTo(systemConfigurationDTO2);
        systemConfigurationDTO1.setId(null);
        assertThat(systemConfigurationDTO1).isNotEqualTo(systemConfigurationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(systemConfigurationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(systemConfigurationMapper.fromId(null)).isNull();
    }
}
