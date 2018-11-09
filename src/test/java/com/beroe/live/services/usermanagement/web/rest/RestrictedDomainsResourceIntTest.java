package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.RestrictedDomains;
import com.beroe.live.services.usermanagement.repository.RestrictedDomainsRepository;
import com.beroe.live.services.usermanagement.service.RestrictedDomainsService;
import com.beroe.live.services.usermanagement.service.dto.RestrictedDomainsDTO;
import com.beroe.live.services.usermanagement.service.mapper.RestrictedDomainsMapper;
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
 * Test class for the RestrictedDomainsResource REST controller.
 *
 * @see RestrictedDomainsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class RestrictedDomainsResourceIntTest {

    private static final String DEFAULT_DOMAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_NAME = "BBBBBBBBBB";

    @Autowired
    private RestrictedDomainsRepository restrictedDomainsRepository;

    @Autowired
    private RestrictedDomainsMapper restrictedDomainsMapper;

    @Autowired
    private RestrictedDomainsService restrictedDomainsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRestrictedDomainsMockMvc;

    private RestrictedDomains restrictedDomains;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestrictedDomainsResource restrictedDomainsResource = new RestrictedDomainsResource(restrictedDomainsService);
        this.restRestrictedDomainsMockMvc = MockMvcBuilders.standaloneSetup(restrictedDomainsResource)
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
    public static RestrictedDomains createEntity(EntityManager em) {
        RestrictedDomains restrictedDomains = new RestrictedDomains()
            .domainName(DEFAULT_DOMAIN_NAME);
        return restrictedDomains;
    }

    @Before
    public void initTest() {
        restrictedDomains = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestrictedDomains() throws Exception {
        int databaseSizeBeforeCreate = restrictedDomainsRepository.findAll().size();

        // Create the RestrictedDomains
        RestrictedDomainsDTO restrictedDomainsDTO = restrictedDomainsMapper.toDto(restrictedDomains);
        restRestrictedDomainsMockMvc.perform(post("/api/restricted-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restrictedDomainsDTO)))
            .andExpect(status().isCreated());

        // Validate the RestrictedDomains in the database
        List<RestrictedDomains> restrictedDomainsList = restrictedDomainsRepository.findAll();
        assertThat(restrictedDomainsList).hasSize(databaseSizeBeforeCreate + 1);
        RestrictedDomains testRestrictedDomains = restrictedDomainsList.get(restrictedDomainsList.size() - 1);
        assertThat(testRestrictedDomains.getDomainName()).isEqualTo(DEFAULT_DOMAIN_NAME);
    }

    @Test
    @Transactional
    public void createRestrictedDomainsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restrictedDomainsRepository.findAll().size();

        // Create the RestrictedDomains with an existing ID
        restrictedDomains.setId(1L);
        RestrictedDomainsDTO restrictedDomainsDTO = restrictedDomainsMapper.toDto(restrictedDomains);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestrictedDomainsMockMvc.perform(post("/api/restricted-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restrictedDomainsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestrictedDomains in the database
        List<RestrictedDomains> restrictedDomainsList = restrictedDomainsRepository.findAll();
        assertThat(restrictedDomainsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDomainNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = restrictedDomainsRepository.findAll().size();
        // set the field null
        restrictedDomains.setDomainName(null);

        // Create the RestrictedDomains, which fails.
        RestrictedDomainsDTO restrictedDomainsDTO = restrictedDomainsMapper.toDto(restrictedDomains);

        restRestrictedDomainsMockMvc.perform(post("/api/restricted-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restrictedDomainsDTO)))
            .andExpect(status().isBadRequest());

        List<RestrictedDomains> restrictedDomainsList = restrictedDomainsRepository.findAll();
        assertThat(restrictedDomainsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRestrictedDomains() throws Exception {
        // Initialize the database
        restrictedDomainsRepository.saveAndFlush(restrictedDomains);

        // Get all the restrictedDomainsList
        restRestrictedDomainsMockMvc.perform(get("/api/restricted-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restrictedDomains.getId().intValue())))
            .andExpect(jsonPath("$.[*].domainName").value(hasItem(DEFAULT_DOMAIN_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRestrictedDomains() throws Exception {
        // Initialize the database
        restrictedDomainsRepository.saveAndFlush(restrictedDomains);

        // Get the restrictedDomains
        restRestrictedDomainsMockMvc.perform(get("/api/restricted-domains/{id}", restrictedDomains.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restrictedDomains.getId().intValue()))
            .andExpect(jsonPath("$.domainName").value(DEFAULT_DOMAIN_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRestrictedDomains() throws Exception {
        // Get the restrictedDomains
        restRestrictedDomainsMockMvc.perform(get("/api/restricted-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestrictedDomains() throws Exception {
        // Initialize the database
        restrictedDomainsRepository.saveAndFlush(restrictedDomains);

        int databaseSizeBeforeUpdate = restrictedDomainsRepository.findAll().size();

        // Update the restrictedDomains
        RestrictedDomains updatedRestrictedDomains = restrictedDomainsRepository.findById(restrictedDomains.getId()).get();
        // Disconnect from session so that the updates on updatedRestrictedDomains are not directly saved in db
        em.detach(updatedRestrictedDomains);
        updatedRestrictedDomains
            .domainName(UPDATED_DOMAIN_NAME);
        RestrictedDomainsDTO restrictedDomainsDTO = restrictedDomainsMapper.toDto(updatedRestrictedDomains);

        restRestrictedDomainsMockMvc.perform(put("/api/restricted-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restrictedDomainsDTO)))
            .andExpect(status().isOk());

        // Validate the RestrictedDomains in the database
        List<RestrictedDomains> restrictedDomainsList = restrictedDomainsRepository.findAll();
        assertThat(restrictedDomainsList).hasSize(databaseSizeBeforeUpdate);
        RestrictedDomains testRestrictedDomains = restrictedDomainsList.get(restrictedDomainsList.size() - 1);
        assertThat(testRestrictedDomains.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRestrictedDomains() throws Exception {
        int databaseSizeBeforeUpdate = restrictedDomainsRepository.findAll().size();

        // Create the RestrictedDomains
        RestrictedDomainsDTO restrictedDomainsDTO = restrictedDomainsMapper.toDto(restrictedDomains);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestrictedDomainsMockMvc.perform(put("/api/restricted-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restrictedDomainsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestrictedDomains in the database
        List<RestrictedDomains> restrictedDomainsList = restrictedDomainsRepository.findAll();
        assertThat(restrictedDomainsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRestrictedDomains() throws Exception {
        // Initialize the database
        restrictedDomainsRepository.saveAndFlush(restrictedDomains);

        int databaseSizeBeforeDelete = restrictedDomainsRepository.findAll().size();

        // Get the restrictedDomains
        restRestrictedDomainsMockMvc.perform(delete("/api/restricted-domains/{id}", restrictedDomains.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RestrictedDomains> restrictedDomainsList = restrictedDomainsRepository.findAll();
        assertThat(restrictedDomainsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestrictedDomains.class);
        RestrictedDomains restrictedDomains1 = new RestrictedDomains();
        restrictedDomains1.setId(1L);
        RestrictedDomains restrictedDomains2 = new RestrictedDomains();
        restrictedDomains2.setId(restrictedDomains1.getId());
        assertThat(restrictedDomains1).isEqualTo(restrictedDomains2);
        restrictedDomains2.setId(2L);
        assertThat(restrictedDomains1).isNotEqualTo(restrictedDomains2);
        restrictedDomains1.setId(null);
        assertThat(restrictedDomains1).isNotEqualTo(restrictedDomains2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestrictedDomainsDTO.class);
        RestrictedDomainsDTO restrictedDomainsDTO1 = new RestrictedDomainsDTO();
        restrictedDomainsDTO1.setId(1L);
        RestrictedDomainsDTO restrictedDomainsDTO2 = new RestrictedDomainsDTO();
        assertThat(restrictedDomainsDTO1).isNotEqualTo(restrictedDomainsDTO2);
        restrictedDomainsDTO2.setId(restrictedDomainsDTO1.getId());
        assertThat(restrictedDomainsDTO1).isEqualTo(restrictedDomainsDTO2);
        restrictedDomainsDTO2.setId(2L);
        assertThat(restrictedDomainsDTO1).isNotEqualTo(restrictedDomainsDTO2);
        restrictedDomainsDTO1.setId(null);
        assertThat(restrictedDomainsDTO1).isNotEqualTo(restrictedDomainsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(restrictedDomainsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(restrictedDomainsMapper.fromId(null)).isNull();
    }
}
