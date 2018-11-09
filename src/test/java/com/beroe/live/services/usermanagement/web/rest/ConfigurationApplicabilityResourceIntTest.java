package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.ConfigurationApplicability;
import com.beroe.live.services.usermanagement.repository.ConfigurationApplicabilityRepository;
import com.beroe.live.services.usermanagement.service.ConfigurationApplicabilityService;
import com.beroe.live.services.usermanagement.service.dto.ConfigurationApplicabilityDTO;
import com.beroe.live.services.usermanagement.service.mapper.ConfigurationApplicabilityMapper;
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
 * Test class for the ConfigurationApplicabilityResource REST controller.
 *
 * @see ConfigurationApplicabilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class ConfigurationApplicabilityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ConfigurationApplicabilityRepository configurationApplicabilityRepository;

    @Autowired
    private ConfigurationApplicabilityMapper configurationApplicabilityMapper;

    @Autowired
    private ConfigurationApplicabilityService configurationApplicabilityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConfigurationApplicabilityMockMvc;

    private ConfigurationApplicability configurationApplicability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfigurationApplicabilityResource configurationApplicabilityResource = new ConfigurationApplicabilityResource(configurationApplicabilityService);
        this.restConfigurationApplicabilityMockMvc = MockMvcBuilders.standaloneSetup(configurationApplicabilityResource)
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
    public static ConfigurationApplicability createEntity(EntityManager em) {
        ConfigurationApplicability configurationApplicability = new ConfigurationApplicability()
            .name(DEFAULT_NAME);
        return configurationApplicability;
    }

    @Before
    public void initTest() {
        configurationApplicability = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfigurationApplicability() throws Exception {
        int databaseSizeBeforeCreate = configurationApplicabilityRepository.findAll().size();

        // Create the ConfigurationApplicability
        ConfigurationApplicabilityDTO configurationApplicabilityDTO = configurationApplicabilityMapper.toDto(configurationApplicability);
        restConfigurationApplicabilityMockMvc.perform(post("/api/configuration-applicabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationApplicabilityDTO)))
            .andExpect(status().isCreated());

        // Validate the ConfigurationApplicability in the database
        List<ConfigurationApplicability> configurationApplicabilityList = configurationApplicabilityRepository.findAll();
        assertThat(configurationApplicabilityList).hasSize(databaseSizeBeforeCreate + 1);
        ConfigurationApplicability testConfigurationApplicability = configurationApplicabilityList.get(configurationApplicabilityList.size() - 1);
        assertThat(testConfigurationApplicability.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createConfigurationApplicabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = configurationApplicabilityRepository.findAll().size();

        // Create the ConfigurationApplicability with an existing ID
        configurationApplicability.setId(1L);
        ConfigurationApplicabilityDTO configurationApplicabilityDTO = configurationApplicabilityMapper.toDto(configurationApplicability);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfigurationApplicabilityMockMvc.perform(post("/api/configuration-applicabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationApplicabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationApplicability in the database
        List<ConfigurationApplicability> configurationApplicabilityList = configurationApplicabilityRepository.findAll();
        assertThat(configurationApplicabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = configurationApplicabilityRepository.findAll().size();
        // set the field null
        configurationApplicability.setName(null);

        // Create the ConfigurationApplicability, which fails.
        ConfigurationApplicabilityDTO configurationApplicabilityDTO = configurationApplicabilityMapper.toDto(configurationApplicability);

        restConfigurationApplicabilityMockMvc.perform(post("/api/configuration-applicabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationApplicabilityDTO)))
            .andExpect(status().isBadRequest());

        List<ConfigurationApplicability> configurationApplicabilityList = configurationApplicabilityRepository.findAll();
        assertThat(configurationApplicabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConfigurationApplicabilities() throws Exception {
        // Initialize the database
        configurationApplicabilityRepository.saveAndFlush(configurationApplicability);

        // Get all the configurationApplicabilityList
        restConfigurationApplicabilityMockMvc.perform(get("/api/configuration-applicabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(configurationApplicability.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getConfigurationApplicability() throws Exception {
        // Initialize the database
        configurationApplicabilityRepository.saveAndFlush(configurationApplicability);

        // Get the configurationApplicability
        restConfigurationApplicabilityMockMvc.perform(get("/api/configuration-applicabilities/{id}", configurationApplicability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(configurationApplicability.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfigurationApplicability() throws Exception {
        // Get the configurationApplicability
        restConfigurationApplicabilityMockMvc.perform(get("/api/configuration-applicabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfigurationApplicability() throws Exception {
        // Initialize the database
        configurationApplicabilityRepository.saveAndFlush(configurationApplicability);

        int databaseSizeBeforeUpdate = configurationApplicabilityRepository.findAll().size();

        // Update the configurationApplicability
        ConfigurationApplicability updatedConfigurationApplicability = configurationApplicabilityRepository.findById(configurationApplicability.getId()).get();
        // Disconnect from session so that the updates on updatedConfigurationApplicability are not directly saved in db
        em.detach(updatedConfigurationApplicability);
        updatedConfigurationApplicability
            .name(UPDATED_NAME);
        ConfigurationApplicabilityDTO configurationApplicabilityDTO = configurationApplicabilityMapper.toDto(updatedConfigurationApplicability);

        restConfigurationApplicabilityMockMvc.perform(put("/api/configuration-applicabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationApplicabilityDTO)))
            .andExpect(status().isOk());

        // Validate the ConfigurationApplicability in the database
        List<ConfigurationApplicability> configurationApplicabilityList = configurationApplicabilityRepository.findAll();
        assertThat(configurationApplicabilityList).hasSize(databaseSizeBeforeUpdate);
        ConfigurationApplicability testConfigurationApplicability = configurationApplicabilityList.get(configurationApplicabilityList.size() - 1);
        assertThat(testConfigurationApplicability.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConfigurationApplicability() throws Exception {
        int databaseSizeBeforeUpdate = configurationApplicabilityRepository.findAll().size();

        // Create the ConfigurationApplicability
        ConfigurationApplicabilityDTO configurationApplicabilityDTO = configurationApplicabilityMapper.toDto(configurationApplicability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfigurationApplicabilityMockMvc.perform(put("/api/configuration-applicabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(configurationApplicabilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConfigurationApplicability in the database
        List<ConfigurationApplicability> configurationApplicabilityList = configurationApplicabilityRepository.findAll();
        assertThat(configurationApplicabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfigurationApplicability() throws Exception {
        // Initialize the database
        configurationApplicabilityRepository.saveAndFlush(configurationApplicability);

        int databaseSizeBeforeDelete = configurationApplicabilityRepository.findAll().size();

        // Get the configurationApplicability
        restConfigurationApplicabilityMockMvc.perform(delete("/api/configuration-applicabilities/{id}", configurationApplicability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConfigurationApplicability> configurationApplicabilityList = configurationApplicabilityRepository.findAll();
        assertThat(configurationApplicabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigurationApplicability.class);
        ConfigurationApplicability configurationApplicability1 = new ConfigurationApplicability();
        configurationApplicability1.setId(1L);
        ConfigurationApplicability configurationApplicability2 = new ConfigurationApplicability();
        configurationApplicability2.setId(configurationApplicability1.getId());
        assertThat(configurationApplicability1).isEqualTo(configurationApplicability2);
        configurationApplicability2.setId(2L);
        assertThat(configurationApplicability1).isNotEqualTo(configurationApplicability2);
        configurationApplicability1.setId(null);
        assertThat(configurationApplicability1).isNotEqualTo(configurationApplicability2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigurationApplicabilityDTO.class);
        ConfigurationApplicabilityDTO configurationApplicabilityDTO1 = new ConfigurationApplicabilityDTO();
        configurationApplicabilityDTO1.setId(1L);
        ConfigurationApplicabilityDTO configurationApplicabilityDTO2 = new ConfigurationApplicabilityDTO();
        assertThat(configurationApplicabilityDTO1).isNotEqualTo(configurationApplicabilityDTO2);
        configurationApplicabilityDTO2.setId(configurationApplicabilityDTO1.getId());
        assertThat(configurationApplicabilityDTO1).isEqualTo(configurationApplicabilityDTO2);
        configurationApplicabilityDTO2.setId(2L);
        assertThat(configurationApplicabilityDTO1).isNotEqualTo(configurationApplicabilityDTO2);
        configurationApplicabilityDTO1.setId(null);
        assertThat(configurationApplicabilityDTO1).isNotEqualTo(configurationApplicabilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(configurationApplicabilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(configurationApplicabilityMapper.fromId(null)).isNull();
    }
}
