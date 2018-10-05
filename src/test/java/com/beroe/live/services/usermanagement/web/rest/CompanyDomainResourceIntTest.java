package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.CompanyDomain;
import com.beroe.live.services.usermanagement.repository.CompanyDomainRepository;
import com.beroe.live.services.usermanagement.service.CompanyDomainService;
import com.beroe.live.services.usermanagement.service.dto.CompanyDomainDTO;
import com.beroe.live.services.usermanagement.service.mapper.CompanyDomainMapper;
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
 * Test class for the CompanyDomainResource REST controller.
 *
 * @see CompanyDomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class CompanyDomainResourceIntTest {

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    @Autowired
    private CompanyDomainRepository companyDomainRepository;

    @Autowired
    private CompanyDomainMapper companyDomainMapper;
    
    @Autowired
    private CompanyDomainService companyDomainService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyDomainMockMvc;

    private CompanyDomain companyDomain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyDomainResource companyDomainResource = new CompanyDomainResource(companyDomainService);
        this.restCompanyDomainMockMvc = MockMvcBuilders.standaloneSetup(companyDomainResource)
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
    public static CompanyDomain createEntity(EntityManager em) {
        CompanyDomain companyDomain = new CompanyDomain()
            .domain(DEFAULT_DOMAIN);
        return companyDomain;
    }

    @Before
    public void initTest() {
        companyDomain = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDomain() throws Exception {
        int databaseSizeBeforeCreate = companyDomainRepository.findAll().size();

        // Create the CompanyDomain
        CompanyDomainDTO companyDomainDTO = companyDomainMapper.toDto(companyDomain);
        restCompanyDomainMockMvc.perform(post("/api/company-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDomain in the database
        List<CompanyDomain> companyDomainList = companyDomainRepository.findAll();
        assertThat(companyDomainList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDomain testCompanyDomain = companyDomainList.get(companyDomainList.size() - 1);
        assertThat(testCompanyDomain.getDomain()).isEqualTo(DEFAULT_DOMAIN);
    }

    @Test
    @Transactional
    public void createCompanyDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyDomainRepository.findAll().size();

        // Create the CompanyDomain with an existing ID
        companyDomain.setId(1L);
        CompanyDomainDTO companyDomainDTO = companyDomainMapper.toDto(companyDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyDomainMockMvc.perform(post("/api/company-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyDomain in the database
        List<CompanyDomain> companyDomainList = companyDomainRepository.findAll();
        assertThat(companyDomainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDomainIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyDomainRepository.findAll().size();
        // set the field null
        companyDomain.setDomain(null);

        // Create the CompanyDomain, which fails.
        CompanyDomainDTO companyDomainDTO = companyDomainMapper.toDto(companyDomain);

        restCompanyDomainMockMvc.perform(post("/api/company-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDomainDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyDomain> companyDomainList = companyDomainRepository.findAll();
        assertThat(companyDomainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyDomains() throws Exception {
        // Initialize the database
        companyDomainRepository.saveAndFlush(companyDomain);

        // Get all the companyDomainList
        restCompanyDomainMockMvc.perform(get("/api/company-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())));
    }
    
    @Test
    @Transactional
    public void getCompanyDomain() throws Exception {
        // Initialize the database
        companyDomainRepository.saveAndFlush(companyDomain);

        // Get the companyDomain
        restCompanyDomainMockMvc.perform(get("/api/company-domains/{id}", companyDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDomain.getId().intValue()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDomain() throws Exception {
        // Get the companyDomain
        restCompanyDomainMockMvc.perform(get("/api/company-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDomain() throws Exception {
        // Initialize the database
        companyDomainRepository.saveAndFlush(companyDomain);

        int databaseSizeBeforeUpdate = companyDomainRepository.findAll().size();

        // Update the companyDomain
        CompanyDomain updatedCompanyDomain = companyDomainRepository.findById(companyDomain.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyDomain are not directly saved in db
        em.detach(updatedCompanyDomain);
        updatedCompanyDomain
            .domain(UPDATED_DOMAIN);
        CompanyDomainDTO companyDomainDTO = companyDomainMapper.toDto(updatedCompanyDomain);

        restCompanyDomainMockMvc.perform(put("/api/company-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDomainDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyDomain in the database
        List<CompanyDomain> companyDomainList = companyDomainRepository.findAll();
        assertThat(companyDomainList).hasSize(databaseSizeBeforeUpdate);
        CompanyDomain testCompanyDomain = companyDomainList.get(companyDomainList.size() - 1);
        assertThat(testCompanyDomain.getDomain()).isEqualTo(UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyDomain() throws Exception {
        int databaseSizeBeforeUpdate = companyDomainRepository.findAll().size();

        // Create the CompanyDomain
        CompanyDomainDTO companyDomainDTO = companyDomainMapper.toDto(companyDomain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyDomainMockMvc.perform(put("/api/company-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyDomain in the database
        List<CompanyDomain> companyDomainList = companyDomainRepository.findAll();
        assertThat(companyDomainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyDomain() throws Exception {
        // Initialize the database
        companyDomainRepository.saveAndFlush(companyDomain);

        int databaseSizeBeforeDelete = companyDomainRepository.findAll().size();

        // Get the companyDomain
        restCompanyDomainMockMvc.perform(delete("/api/company-domains/{id}", companyDomain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDomain> companyDomainList = companyDomainRepository.findAll();
        assertThat(companyDomainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDomain.class);
        CompanyDomain companyDomain1 = new CompanyDomain();
        companyDomain1.setId(1L);
        CompanyDomain companyDomain2 = new CompanyDomain();
        companyDomain2.setId(companyDomain1.getId());
        assertThat(companyDomain1).isEqualTo(companyDomain2);
        companyDomain2.setId(2L);
        assertThat(companyDomain1).isNotEqualTo(companyDomain2);
        companyDomain1.setId(null);
        assertThat(companyDomain1).isNotEqualTo(companyDomain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDomainDTO.class);
        CompanyDomainDTO companyDomainDTO1 = new CompanyDomainDTO();
        companyDomainDTO1.setId(1L);
        CompanyDomainDTO companyDomainDTO2 = new CompanyDomainDTO();
        assertThat(companyDomainDTO1).isNotEqualTo(companyDomainDTO2);
        companyDomainDTO2.setId(companyDomainDTO1.getId());
        assertThat(companyDomainDTO1).isEqualTo(companyDomainDTO2);
        companyDomainDTO2.setId(2L);
        assertThat(companyDomainDTO1).isNotEqualTo(companyDomainDTO2);
        companyDomainDTO1.setId(null);
        assertThat(companyDomainDTO1).isNotEqualTo(companyDomainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyDomainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyDomainMapper.fromId(null)).isNull();
    }
}
