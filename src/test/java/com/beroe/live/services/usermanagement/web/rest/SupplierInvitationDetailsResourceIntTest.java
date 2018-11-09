package com.beroe.live.services.usermanagement.web.rest;

import com.beroe.live.services.usermanagement.UsermanagementApp;

import com.beroe.live.services.usermanagement.domain.SupplierInvitationDetails;
import com.beroe.live.services.usermanagement.repository.SupplierInvitationDetailsRepository;
import com.beroe.live.services.usermanagement.service.SupplierInvitationDetailsService;
import com.beroe.live.services.usermanagement.service.dto.SupplierInvitationDetailsDTO;
import com.beroe.live.services.usermanagement.service.mapper.SupplierInvitationDetailsMapper;
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

import com.beroe.live.services.usermanagement.domain.enumeration.SupplierDataProvider;
/**
 * Test class for the SupplierInvitationDetailsResource REST controller.
 *
 * @see SupplierInvitationDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsermanagementApp.class)
public class SupplierInvitationDetailsResourceIntTest {

    private static final String DEFAULT_SUPPLIER_DATA_PROVIDER_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_DATA_PROVIDER_KEY = "BBBBBBBBBB";

    private static final SupplierDataProvider DEFAULT_DATA_PROVIDER = SupplierDataProvider.DNB;
    private static final SupplierDataProvider UPDATED_DATA_PROVIDER = SupplierDataProvider.DVB;

    @Autowired
    private SupplierInvitationDetailsRepository supplierInvitationDetailsRepository;

    @Autowired
    private SupplierInvitationDetailsMapper supplierInvitationDetailsMapper;

    @Autowired
    private SupplierInvitationDetailsService supplierInvitationDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSupplierInvitationDetailsMockMvc;

    private SupplierInvitationDetails supplierInvitationDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierInvitationDetailsResource supplierInvitationDetailsResource = new SupplierInvitationDetailsResource(supplierInvitationDetailsService);
        this.restSupplierInvitationDetailsMockMvc = MockMvcBuilders.standaloneSetup(supplierInvitationDetailsResource)
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
    public static SupplierInvitationDetails createEntity(EntityManager em) {
        SupplierInvitationDetails supplierInvitationDetails = new SupplierInvitationDetails()
            .supplierDataProviderKey(DEFAULT_SUPPLIER_DATA_PROVIDER_KEY)
            .dataProvider(DEFAULT_DATA_PROVIDER);
        return supplierInvitationDetails;
    }

    @Before
    public void initTest() {
        supplierInvitationDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierInvitationDetails() throws Exception {
        int databaseSizeBeforeCreate = supplierInvitationDetailsRepository.findAll().size();

        // Create the SupplierInvitationDetails
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = supplierInvitationDetailsMapper.toDto(supplierInvitationDetails);
        restSupplierInvitationDetailsMockMvc.perform(post("/api/supplier-invitation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierInvitationDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierInvitationDetails in the database
        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierInvitationDetails testSupplierInvitationDetails = supplierInvitationDetailsList.get(supplierInvitationDetailsList.size() - 1);
        assertThat(testSupplierInvitationDetails.getSupplierDataProviderKey()).isEqualTo(DEFAULT_SUPPLIER_DATA_PROVIDER_KEY);
        assertThat(testSupplierInvitationDetails.getDataProvider()).isEqualTo(DEFAULT_DATA_PROVIDER);
    }

    @Test
    @Transactional
    public void createSupplierInvitationDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierInvitationDetailsRepository.findAll().size();

        // Create the SupplierInvitationDetails with an existing ID
        supplierInvitationDetails.setId(1L);
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = supplierInvitationDetailsMapper.toDto(supplierInvitationDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierInvitationDetailsMockMvc.perform(post("/api/supplier-invitation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierInvitationDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierInvitationDetails in the database
        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSupplierDataProviderKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierInvitationDetailsRepository.findAll().size();
        // set the field null
        supplierInvitationDetails.setSupplierDataProviderKey(null);

        // Create the SupplierInvitationDetails, which fails.
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = supplierInvitationDetailsMapper.toDto(supplierInvitationDetails);

        restSupplierInvitationDetailsMockMvc.perform(post("/api/supplier-invitation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierInvitationDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataProviderIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierInvitationDetailsRepository.findAll().size();
        // set the field null
        supplierInvitationDetails.setDataProvider(null);

        // Create the SupplierInvitationDetails, which fails.
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = supplierInvitationDetailsMapper.toDto(supplierInvitationDetails);

        restSupplierInvitationDetailsMockMvc.perform(post("/api/supplier-invitation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierInvitationDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierInvitationDetails() throws Exception {
        // Initialize the database
        supplierInvitationDetailsRepository.saveAndFlush(supplierInvitationDetails);

        // Get all the supplierInvitationDetailsList
        restSupplierInvitationDetailsMockMvc.perform(get("/api/supplier-invitation-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierInvitationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierDataProviderKey").value(hasItem(DEFAULT_SUPPLIER_DATA_PROVIDER_KEY.toString())))
            .andExpect(jsonPath("$.[*].dataProvider").value(hasItem(DEFAULT_DATA_PROVIDER.toString())));
    }
    
    @Test
    @Transactional
    public void getSupplierInvitationDetails() throws Exception {
        // Initialize the database
        supplierInvitationDetailsRepository.saveAndFlush(supplierInvitationDetails);

        // Get the supplierInvitationDetails
        restSupplierInvitationDetailsMockMvc.perform(get("/api/supplier-invitation-details/{id}", supplierInvitationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierInvitationDetails.getId().intValue()))
            .andExpect(jsonPath("$.supplierDataProviderKey").value(DEFAULT_SUPPLIER_DATA_PROVIDER_KEY.toString()))
            .andExpect(jsonPath("$.dataProvider").value(DEFAULT_DATA_PROVIDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplierInvitationDetails() throws Exception {
        // Get the supplierInvitationDetails
        restSupplierInvitationDetailsMockMvc.perform(get("/api/supplier-invitation-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierInvitationDetails() throws Exception {
        // Initialize the database
        supplierInvitationDetailsRepository.saveAndFlush(supplierInvitationDetails);

        int databaseSizeBeforeUpdate = supplierInvitationDetailsRepository.findAll().size();

        // Update the supplierInvitationDetails
        SupplierInvitationDetails updatedSupplierInvitationDetails = supplierInvitationDetailsRepository.findById(supplierInvitationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierInvitationDetails are not directly saved in db
        em.detach(updatedSupplierInvitationDetails);
        updatedSupplierInvitationDetails
            .supplierDataProviderKey(UPDATED_SUPPLIER_DATA_PROVIDER_KEY)
            .dataProvider(UPDATED_DATA_PROVIDER);
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = supplierInvitationDetailsMapper.toDto(updatedSupplierInvitationDetails);

        restSupplierInvitationDetailsMockMvc.perform(put("/api/supplier-invitation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierInvitationDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierInvitationDetails in the database
        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeUpdate);
        SupplierInvitationDetails testSupplierInvitationDetails = supplierInvitationDetailsList.get(supplierInvitationDetailsList.size() - 1);
        assertThat(testSupplierInvitationDetails.getSupplierDataProviderKey()).isEqualTo(UPDATED_SUPPLIER_DATA_PROVIDER_KEY);
        assertThat(testSupplierInvitationDetails.getDataProvider()).isEqualTo(UPDATED_DATA_PROVIDER);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierInvitationDetails() throws Exception {
        int databaseSizeBeforeUpdate = supplierInvitationDetailsRepository.findAll().size();

        // Create the SupplierInvitationDetails
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO = supplierInvitationDetailsMapper.toDto(supplierInvitationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierInvitationDetailsMockMvc.perform(put("/api/supplier-invitation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierInvitationDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierInvitationDetails in the database
        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierInvitationDetails() throws Exception {
        // Initialize the database
        supplierInvitationDetailsRepository.saveAndFlush(supplierInvitationDetails);

        int databaseSizeBeforeDelete = supplierInvitationDetailsRepository.findAll().size();

        // Get the supplierInvitationDetails
        restSupplierInvitationDetailsMockMvc.perform(delete("/api/supplier-invitation-details/{id}", supplierInvitationDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplierInvitationDetails> supplierInvitationDetailsList = supplierInvitationDetailsRepository.findAll();
        assertThat(supplierInvitationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierInvitationDetails.class);
        SupplierInvitationDetails supplierInvitationDetails1 = new SupplierInvitationDetails();
        supplierInvitationDetails1.setId(1L);
        SupplierInvitationDetails supplierInvitationDetails2 = new SupplierInvitationDetails();
        supplierInvitationDetails2.setId(supplierInvitationDetails1.getId());
        assertThat(supplierInvitationDetails1).isEqualTo(supplierInvitationDetails2);
        supplierInvitationDetails2.setId(2L);
        assertThat(supplierInvitationDetails1).isNotEqualTo(supplierInvitationDetails2);
        supplierInvitationDetails1.setId(null);
        assertThat(supplierInvitationDetails1).isNotEqualTo(supplierInvitationDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierInvitationDetailsDTO.class);
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO1 = new SupplierInvitationDetailsDTO();
        supplierInvitationDetailsDTO1.setId(1L);
        SupplierInvitationDetailsDTO supplierInvitationDetailsDTO2 = new SupplierInvitationDetailsDTO();
        assertThat(supplierInvitationDetailsDTO1).isNotEqualTo(supplierInvitationDetailsDTO2);
        supplierInvitationDetailsDTO2.setId(supplierInvitationDetailsDTO1.getId());
        assertThat(supplierInvitationDetailsDTO1).isEqualTo(supplierInvitationDetailsDTO2);
        supplierInvitationDetailsDTO2.setId(2L);
        assertThat(supplierInvitationDetailsDTO1).isNotEqualTo(supplierInvitationDetailsDTO2);
        supplierInvitationDetailsDTO1.setId(null);
        assertThat(supplierInvitationDetailsDTO1).isNotEqualTo(supplierInvitationDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplierInvitationDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplierInvitationDetailsMapper.fromId(null)).isNull();
    }
}
