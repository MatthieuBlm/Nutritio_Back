package com.mac.nutritio.web.rest;

import com.mac.nutritio.NutritioBackApp;

import com.mac.nutritio.domain.IngredientEntry;
import com.mac.nutritio.repository.IngredientEntryRepository;
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
import java.util.List;

import static com.mac.nutritio.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mac.nutritio.domain.enumeration.Unit;
/**
 * Test class for the IngredientEntryResource REST controller.
 *
 * @see IngredientEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NutritioBackApp.class)
public class IngredientEntryResourceIntTest {

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final Unit DEFAULT_UNIT = Unit.GRAMM;
    private static final Unit UPDATED_UNIT = Unit.MILLILITRE;

    @Autowired
    private IngredientEntryRepository ingredientEntryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIngredientEntryMockMvc;

    private IngredientEntry ingredientEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IngredientEntryResource ingredientEntryResource = new IngredientEntryResource(ingredientEntryRepository);
        this.restIngredientEntryMockMvc = MockMvcBuilders.standaloneSetup(ingredientEntryResource)
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
    public static IngredientEntry createEntity(EntityManager em) {
        IngredientEntry ingredientEntry = new IngredientEntry()
            .amount(DEFAULT_AMOUNT)
            .unit(DEFAULT_UNIT);
        return ingredientEntry;
    }

    @Before
    public void initTest() {
        ingredientEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientEntry() throws Exception {
        int databaseSizeBeforeCreate = ingredientEntryRepository.findAll().size();

        // Create the IngredientEntry
        restIngredientEntryMockMvc.perform(post("/api/ingredient-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientEntry)))
            .andExpect(status().isCreated());

        // Validate the IngredientEntry in the database
        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientEntry testIngredientEntry = ingredientEntryList.get(ingredientEntryList.size() - 1);
        assertThat(testIngredientEntry.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testIngredientEntry.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    public void createIngredientEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientEntryRepository.findAll().size();

        // Create the IngredientEntry with an existing ID
        ingredientEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientEntryMockMvc.perform(post("/api/ingredient-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientEntry)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientEntry in the database
        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientEntryRepository.findAll().size();
        // set the field null
        ingredientEntry.setAmount(null);

        // Create the IngredientEntry, which fails.

        restIngredientEntryMockMvc.perform(post("/api/ingredient-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientEntry)))
            .andExpect(status().isBadRequest());

        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = ingredientEntryRepository.findAll().size();
        // set the field null
        ingredientEntry.setUnit(null);

        // Create the IngredientEntry, which fails.

        restIngredientEntryMockMvc.perform(post("/api/ingredient-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientEntry)))
            .andExpect(status().isBadRequest());

        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIngredientEntries() throws Exception {
        // Initialize the database
        ingredientEntryRepository.saveAndFlush(ingredientEntry);

        // Get all the ingredientEntryList
        restIngredientEntryMockMvc.perform(get("/api/ingredient-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }

    @Test
    @Transactional
    public void getIngredientEntry() throws Exception {
        // Initialize the database
        ingredientEntryRepository.saveAndFlush(ingredientEntry);

        // Get the ingredientEntry
        restIngredientEntryMockMvc.perform(get("/api/ingredient-entries/{id}", ingredientEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientEntry.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientEntry() throws Exception {
        // Get the ingredientEntry
        restIngredientEntryMockMvc.perform(get("/api/ingredient-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientEntry() throws Exception {
        // Initialize the database
        ingredientEntryRepository.saveAndFlush(ingredientEntry);
        int databaseSizeBeforeUpdate = ingredientEntryRepository.findAll().size();

        // Update the ingredientEntry
        IngredientEntry updatedIngredientEntry = ingredientEntryRepository.findOne(ingredientEntry.getId());
        // Disconnect from session so that the updates on updatedIngredientEntry are not directly saved in db
        em.detach(updatedIngredientEntry);
        updatedIngredientEntry
            .amount(UPDATED_AMOUNT)
            .unit(UPDATED_UNIT);

        restIngredientEntryMockMvc.perform(put("/api/ingredient-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIngredientEntry)))
            .andExpect(status().isOk());

        // Validate the IngredientEntry in the database
        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeUpdate);
        IngredientEntry testIngredientEntry = ingredientEntryList.get(ingredientEntryList.size() - 1);
        assertThat(testIngredientEntry.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testIngredientEntry.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientEntry() throws Exception {
        int databaseSizeBeforeUpdate = ingredientEntryRepository.findAll().size();

        // Create the IngredientEntry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIngredientEntryMockMvc.perform(put("/api/ingredient-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ingredientEntry)))
            .andExpect(status().isCreated());

        // Validate the IngredientEntry in the database
        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIngredientEntry() throws Exception {
        // Initialize the database
        ingredientEntryRepository.saveAndFlush(ingredientEntry);
        int databaseSizeBeforeDelete = ingredientEntryRepository.findAll().size();

        // Get the ingredientEntry
        restIngredientEntryMockMvc.perform(delete("/api/ingredient-entries/{id}", ingredientEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IngredientEntry> ingredientEntryList = ingredientEntryRepository.findAll();
        assertThat(ingredientEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientEntry.class);
        IngredientEntry ingredientEntry1 = new IngredientEntry();
        ingredientEntry1.setId(1L);
        IngredientEntry ingredientEntry2 = new IngredientEntry();
        ingredientEntry2.setId(ingredientEntry1.getId());
        assertThat(ingredientEntry1).isEqualTo(ingredientEntry2);
        ingredientEntry2.setId(2L);
        assertThat(ingredientEntry1).isNotEqualTo(ingredientEntry2);
        ingredientEntry1.setId(null);
        assertThat(ingredientEntry1).isNotEqualTo(ingredientEntry2);
    }
}
