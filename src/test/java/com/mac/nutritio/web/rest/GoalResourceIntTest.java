package com.mac.nutritio.web.rest;

import com.mac.nutritio.NutritioBackApp;

import com.mac.nutritio.domain.Goal;
import com.mac.nutritio.repository.GoalRepository;
import com.mac.nutritio.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mac.nutritio.web.rest.TestUtil.sameInstant;
import static com.mac.nutritio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GoalResource REST controller.
 *
 * @see GoalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NutritioBackApp.class)
public class GoalResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_ENERGY = 1L;
    private static final Long UPDATED_ENERGY = 2L;

    private static final Long DEFAULT_PROTEIN = 1L;
    private static final Long UPDATED_PROTEIN = 2L;

    private static final Long DEFAULT_CARBOHYDRATE = 1L;
    private static final Long UPDATED_CARBOHYDRATE = 2L;

    private static final Long DEFAULT_FAT = 1L;
    private static final Long UPDATED_FAT = 2L;

    private static final Long DEFAULT_SUGAR = 1L;
    private static final Long UPDATED_SUGAR = 2L;

    private static final Long DEFAULT_SATURATED_FAT = 1L;
    private static final Long UPDATED_SATURATED_FAT = 2L;

    private static final Long DEFAULT_FIBRE = 1L;
    private static final Long UPDATED_FIBRE = 2L;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGoalMockMvc;

    private Goal goal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoalResource goalResource = new GoalResource(goalRepository);
        this.restGoalMockMvc = MockMvcBuilders.standaloneSetup(goalResource)
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
    public static Goal createEntity(EntityManager em) {
        Goal goal = new Goal()
            .date(DEFAULT_DATE)
            .energy(DEFAULT_ENERGY)
            .protein(DEFAULT_PROTEIN)
            .carbohydrate(DEFAULT_CARBOHYDRATE)
            .fat(DEFAULT_FAT)
            .sugar(DEFAULT_SUGAR)
            .saturatedFat(DEFAULT_SATURATED_FAT)
            .fibre(DEFAULT_FIBRE);
        return goal;
    }

    @Before
    public void initTest() {
        goal = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoal() throws Exception {
        int databaseSizeBeforeCreate = goalRepository.findAll().size();

        // Create the Goal
        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isCreated());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
        Goal testGoal = goalList.get(goalList.size() - 1);
        assertThat(testGoal.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testGoal.getEnergy()).isEqualTo(DEFAULT_ENERGY);
        assertThat(testGoal.getProtein()).isEqualTo(DEFAULT_PROTEIN);
        assertThat(testGoal.getCarbohydrate()).isEqualTo(DEFAULT_CARBOHYDRATE);
        assertThat(testGoal.getFat()).isEqualTo(DEFAULT_FAT);
        assertThat(testGoal.getSugar()).isEqualTo(DEFAULT_SUGAR);
        assertThat(testGoal.getSaturatedFat()).isEqualTo(DEFAULT_SATURATED_FAT);
        assertThat(testGoal.getFibre()).isEqualTo(DEFAULT_FIBRE);
    }

    @Test
    @Transactional
    public void createGoalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goalRepository.findAll().size();

        // Create the Goal with an existing ID
        goal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isBadRequest());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGoals() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList
        restGoalMockMvc.perform(get("/api/goals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goal.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].energy").value(hasItem(DEFAULT_ENERGY.intValue())))
            .andExpect(jsonPath("$.[*].protein").value(hasItem(DEFAULT_PROTEIN.intValue())))
            .andExpect(jsonPath("$.[*].carbohydrate").value(hasItem(DEFAULT_CARBOHYDRATE.intValue())))
            .andExpect(jsonPath("$.[*].fat").value(hasItem(DEFAULT_FAT.intValue())))
            .andExpect(jsonPath("$.[*].sugar").value(hasItem(DEFAULT_SUGAR.intValue())))
            .andExpect(jsonPath("$.[*].saturatedFat").value(hasItem(DEFAULT_SATURATED_FAT.intValue())))
            .andExpect(jsonPath("$.[*].fibre").value(hasItem(DEFAULT_FIBRE.intValue())));
    }

    @Test
    @Transactional
    public void getGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", goal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goal.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.energy").value(DEFAULT_ENERGY.intValue()))
            .andExpect(jsonPath("$.protein").value(DEFAULT_PROTEIN.intValue()))
            .andExpect(jsonPath("$.carbohydrate").value(DEFAULT_CARBOHYDRATE.intValue()))
            .andExpect(jsonPath("$.fat").value(DEFAULT_FAT.intValue()))
            .andExpect(jsonPath("$.sugar").value(DEFAULT_SUGAR.intValue()))
            .andExpect(jsonPath("$.saturatedFat").value(DEFAULT_SATURATED_FAT.intValue()))
            .andExpect(jsonPath("$.fibre").value(DEFAULT_FIBRE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGoal() throws Exception {
        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);
        int databaseSizeBeforeUpdate = goalRepository.findAll().size();

        // Update the goal
        Goal updatedGoal = goalRepository.findOne(goal.getId());
        // Disconnect from session so that the updates on updatedGoal are not directly saved in db
        em.detach(updatedGoal);
        updatedGoal
            .date(UPDATED_DATE)
            .energy(UPDATED_ENERGY)
            .protein(UPDATED_PROTEIN)
            .carbohydrate(UPDATED_CARBOHYDRATE)
            .fat(UPDATED_FAT)
            .sugar(UPDATED_SUGAR)
            .saturatedFat(UPDATED_SATURATED_FAT)
            .fibre(UPDATED_FIBRE);

        restGoalMockMvc.perform(put("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoal)))
            .andExpect(status().isOk());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeUpdate);
        Goal testGoal = goalList.get(goalList.size() - 1);
        assertThat(testGoal.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testGoal.getEnergy()).isEqualTo(UPDATED_ENERGY);
        assertThat(testGoal.getProtein()).isEqualTo(UPDATED_PROTEIN);
        assertThat(testGoal.getCarbohydrate()).isEqualTo(UPDATED_CARBOHYDRATE);
        assertThat(testGoal.getFat()).isEqualTo(UPDATED_FAT);
        assertThat(testGoal.getSugar()).isEqualTo(UPDATED_SUGAR);
        assertThat(testGoal.getSaturatedFat()).isEqualTo(UPDATED_SATURATED_FAT);
        assertThat(testGoal.getFibre()).isEqualTo(UPDATED_FIBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingGoal() throws Exception {
        int databaseSizeBeforeUpdate = goalRepository.findAll().size();

        // Create the Goal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGoalMockMvc.perform(put("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goal)))
            .andExpect(status().isCreated());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);
        int databaseSizeBeforeDelete = goalRepository.findAll().size();

        // Get the goal
        restGoalMockMvc.perform(delete("/api/goals/{id}", goal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goal.class);
        Goal goal1 = new Goal();
        goal1.setId(1L);
        Goal goal2 = new Goal();
        goal2.setId(goal1.getId());
        assertThat(goal1).isEqualTo(goal2);
        goal2.setId(2L);
        assertThat(goal1).isNotEqualTo(goal2);
        goal1.setId(null);
        assertThat(goal1).isNotEqualTo(goal2);
    }
}
