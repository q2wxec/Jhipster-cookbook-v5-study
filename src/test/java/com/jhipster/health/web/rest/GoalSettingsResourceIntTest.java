package com.jhipster.health.web.rest;

import com.jhipster.health.TwentyOnePointsApp;

import com.jhipster.health.domain.GoalSettings;
import com.jhipster.health.domain.User;
import com.jhipster.health.repository.GoalSettingsRepository;
import com.jhipster.health.repository.search.GoalSettingsSearchRepository;
import com.jhipster.health.service.GoalSettingsService;
import com.jhipster.health.service.dto.GoalSettingsDTO;
import com.jhipster.health.service.mapper.GoalSettingsMapper;
import com.jhipster.health.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.jhipster.health.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.health.domain.enumeration.Units;
/**
 * Test class for the GoalSettingsResource REST controller.
 *
 * @see GoalSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TwentyOnePointsApp.class)
public class GoalSettingsResourceIntTest {

    private static final Integer DEFAULT_WEEKLY_GOAL = 10;
    private static final Integer UPDATED_WEEKLY_GOAL = 11;

    private static final Units DEFAULT_WEIGHT_UNITS = Units.KG;
    private static final Units UPDATED_WEIGHT_UNITS = Units.LB;

    @Autowired
    private GoalSettingsRepository goalSettingsRepository;

    @Autowired
    private GoalSettingsMapper goalSettingsMapper;

    @Autowired
    private GoalSettingsService goalSettingsService;

    /**
     * This repository is mocked in the com.jhipster.health.repository.search test package.
     *
     * @see com.jhipster.health.repository.search.GoalSettingsSearchRepositoryMockConfiguration
     */
    @Autowired
    private GoalSettingsSearchRepository mockGoalSettingsSearchRepository;

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

    private MockMvc restGoalSettingsMockMvc;

    private GoalSettings goalSettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoalSettingsResource goalSettingsResource = new GoalSettingsResource(goalSettingsService);
        this.restGoalSettingsMockMvc = MockMvcBuilders.standaloneSetup(goalSettingsResource)
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
    public static GoalSettings createEntity(EntityManager em) {
        GoalSettings goalSettings = new GoalSettings()
            .weeklyGoal(DEFAULT_WEEKLY_GOAL)
            .weightUnits(DEFAULT_WEIGHT_UNITS);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        goalSettings.setUser(user);
        return goalSettings;
    }

    @Before
    public void initTest() {
        goalSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoalSettings() throws Exception {
        int databaseSizeBeforeCreate = goalSettingsRepository.findAll().size();

        // Create the GoalSettings
        GoalSettingsDTO goalSettingsDTO = goalSettingsMapper.toDto(goalSettings);
        restGoalSettingsMockMvc.perform(post("/api/goal-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalSettingsDTO)))
            .andExpect(status().isCreated());

        // Validate the GoalSettings in the database
        List<GoalSettings> goalSettingsList = goalSettingsRepository.findAll();
        assertThat(goalSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        GoalSettings testGoalSettings = goalSettingsList.get(goalSettingsList.size() - 1);
        assertThat(testGoalSettings.getWeeklyGoal()).isEqualTo(DEFAULT_WEEKLY_GOAL);
        assertThat(testGoalSettings.getWeightUnits()).isEqualTo(DEFAULT_WEIGHT_UNITS);

        // Validate the id for MapsId, the ids must be same
        assertThat(testGoalSettings.getId()).isEqualTo(testGoalSettings.getUser().getId());

        // Validate the GoalSettings in Elasticsearch
        verify(mockGoalSettingsSearchRepository, times(1)).save(testGoalSettings);
    }

    @Test
    @Transactional
    public void createGoalSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goalSettingsRepository.findAll().size();

        // Create the GoalSettings with an existing ID
        goalSettings.setId(1L);
        GoalSettingsDTO goalSettingsDTO = goalSettingsMapper.toDto(goalSettings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalSettingsMockMvc.perform(post("/api/goal-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoalSettings in the database
        List<GoalSettings> goalSettingsList = goalSettingsRepository.findAll();
        assertThat(goalSettingsList).hasSize(databaseSizeBeforeCreate);

        // Validate the GoalSettings in Elasticsearch
        verify(mockGoalSettingsSearchRepository, times(0)).save(goalSettings);
    }

    @Test
    @Transactional
    public void checkWeightUnitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = goalSettingsRepository.findAll().size();
        // set the field null
        goalSettings.setWeightUnits(null);

        // Create the GoalSettings, which fails.
        GoalSettingsDTO goalSettingsDTO = goalSettingsMapper.toDto(goalSettings);

        restGoalSettingsMockMvc.perform(post("/api/goal-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalSettingsDTO)))
            .andExpect(status().isBadRequest());

        List<GoalSettings> goalSettingsList = goalSettingsRepository.findAll();
        assertThat(goalSettingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoalSettings() throws Exception {
        // Initialize the database
        goalSettingsRepository.saveAndFlush(goalSettings);

        // Get all the goalSettingsList
        restGoalSettingsMockMvc.perform(get("/api/goal-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].weeklyGoal").value(hasItem(DEFAULT_WEEKLY_GOAL)))
            .andExpect(jsonPath("$.[*].weightUnits").value(hasItem(DEFAULT_WEIGHT_UNITS.toString())));
    }
    
    @Test
    @Transactional
    public void getGoalSettings() throws Exception {
        // Initialize the database
        goalSettingsRepository.saveAndFlush(goalSettings);

        // Get the goalSettings
        restGoalSettingsMockMvc.perform(get("/api/goal-settings/{id}", goalSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goalSettings.getId().intValue()))
            .andExpect(jsonPath("$.weeklyGoal").value(DEFAULT_WEEKLY_GOAL))
            .andExpect(jsonPath("$.weightUnits").value(DEFAULT_WEIGHT_UNITS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGoalSettings() throws Exception {
        // Get the goalSettings
        restGoalSettingsMockMvc.perform(get("/api/goal-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoalSettings() throws Exception {
        // Initialize the database
        goalSettingsRepository.saveAndFlush(goalSettings);

        int databaseSizeBeforeUpdate = goalSettingsRepository.findAll().size();

        // Update the goalSettings
        GoalSettings updatedGoalSettings = goalSettingsRepository.findById(goalSettings.getId()).get();
        // Disconnect from session so that the updates on updatedGoalSettings are not directly saved in db
        em.detach(updatedGoalSettings);
        updatedGoalSettings
            .weeklyGoal(UPDATED_WEEKLY_GOAL)
            .weightUnits(UPDATED_WEIGHT_UNITS);
        GoalSettingsDTO goalSettingsDTO = goalSettingsMapper.toDto(updatedGoalSettings);

        restGoalSettingsMockMvc.perform(put("/api/goal-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalSettingsDTO)))
            .andExpect(status().isOk());

        // Validate the GoalSettings in the database
        List<GoalSettings> goalSettingsList = goalSettingsRepository.findAll();
        assertThat(goalSettingsList).hasSize(databaseSizeBeforeUpdate);
        GoalSettings testGoalSettings = goalSettingsList.get(goalSettingsList.size() - 1);
        assertThat(testGoalSettings.getWeeklyGoal()).isEqualTo(UPDATED_WEEKLY_GOAL);
        assertThat(testGoalSettings.getWeightUnits()).isEqualTo(UPDATED_WEIGHT_UNITS);

        // Validate the GoalSettings in Elasticsearch
        verify(mockGoalSettingsSearchRepository, times(1)).save(testGoalSettings);
    }

    @Test
    @Transactional
    public void updateNonExistingGoalSettings() throws Exception {
        int databaseSizeBeforeUpdate = goalSettingsRepository.findAll().size();

        // Create the GoalSettings
        GoalSettingsDTO goalSettingsDTO = goalSettingsMapper.toDto(goalSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalSettingsMockMvc.perform(put("/api/goal-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoalSettings in the database
        List<GoalSettings> goalSettingsList = goalSettingsRepository.findAll();
        assertThat(goalSettingsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GoalSettings in Elasticsearch
        verify(mockGoalSettingsSearchRepository, times(0)).save(goalSettings);
    }

    @Test
    @Transactional
    public void deleteGoalSettings() throws Exception {
        // Initialize the database
        goalSettingsRepository.saveAndFlush(goalSettings);

        int databaseSizeBeforeDelete = goalSettingsRepository.findAll().size();

        // Delete the goalSettings
        restGoalSettingsMockMvc.perform(delete("/api/goal-settings/{id}", goalSettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GoalSettings> goalSettingsList = goalSettingsRepository.findAll();
        assertThat(goalSettingsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GoalSettings in Elasticsearch
        verify(mockGoalSettingsSearchRepository, times(1)).deleteById(goalSettings.getId());
    }

    @Test
    @Transactional
    public void searchGoalSettings() throws Exception {
        // Initialize the database
        goalSettingsRepository.saveAndFlush(goalSettings);
        when(mockGoalSettingsSearchRepository.search(queryStringQuery("id:" + goalSettings.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(goalSettings), PageRequest.of(0, 1), 1));
        // Search the goalSettings
        restGoalSettingsMockMvc.perform(get("/api/_search/goal-settings?query=id:" + goalSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].weeklyGoal").value(hasItem(DEFAULT_WEEKLY_GOAL)))
            .andExpect(jsonPath("$.[*].weightUnits").value(hasItem(DEFAULT_WEIGHT_UNITS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalSettings.class);
        GoalSettings goalSettings1 = new GoalSettings();
        goalSettings1.setId(1L);
        GoalSettings goalSettings2 = new GoalSettings();
        goalSettings2.setId(goalSettings1.getId());
        assertThat(goalSettings1).isEqualTo(goalSettings2);
        goalSettings2.setId(2L);
        assertThat(goalSettings1).isNotEqualTo(goalSettings2);
        goalSettings1.setId(null);
        assertThat(goalSettings1).isNotEqualTo(goalSettings2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalSettingsDTO.class);
        GoalSettingsDTO goalSettingsDTO1 = new GoalSettingsDTO();
        goalSettingsDTO1.setId(1L);
        GoalSettingsDTO goalSettingsDTO2 = new GoalSettingsDTO();
        assertThat(goalSettingsDTO1).isNotEqualTo(goalSettingsDTO2);
        goalSettingsDTO2.setId(goalSettingsDTO1.getId());
        assertThat(goalSettingsDTO1).isEqualTo(goalSettingsDTO2);
        goalSettingsDTO2.setId(2L);
        assertThat(goalSettingsDTO1).isNotEqualTo(goalSettingsDTO2);
        goalSettingsDTO1.setId(null);
        assertThat(goalSettingsDTO1).isNotEqualTo(goalSettingsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(goalSettingsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(goalSettingsMapper.fromId(null)).isNull();
    }
}
