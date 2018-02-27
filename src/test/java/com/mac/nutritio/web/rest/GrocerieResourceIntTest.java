package com.mac.nutritio.web.rest;

import com.mac.nutritio.NutritioBackApp;

import com.mac.nutritio.domain.Grocerie;
import com.mac.nutritio.repository.GrocerieRepository;
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

/**
 * Test class for the GrocerieResource REST controller.
 *
 * @see GrocerieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NutritioBackApp.class)
public class GrocerieResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GrocerieRepository grocerieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrocerieMockMvc;

    private Grocerie grocerie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrocerieResource grocerieResource = new GrocerieResource(grocerieRepository);
        this.restGrocerieMockMvc = MockMvcBuilders.standaloneSetup(grocerieResource)
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
    public static Grocerie createEntity(EntityManager em) {
        Grocerie grocerie = new Grocerie()
            .name(DEFAULT_NAME);
        return grocerie;
    }

    @Before
    public void initTest() {
        grocerie = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrocerie() throws Exception {
        int databaseSizeBeforeCreate = grocerieRepository.findAll().size();

        // Create the Grocerie
        restGrocerieMockMvc.perform(post("/api/groceries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grocerie)))
            .andExpect(status().isCreated());

        // Validate the Grocerie in the database
        List<Grocerie> grocerieList = grocerieRepository.findAll();
        assertThat(grocerieList).hasSize(databaseSizeBeforeCreate + 1);
        Grocerie testGrocerie = grocerieList.get(grocerieList.size() - 1);
        assertThat(testGrocerie.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createGrocerieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grocerieRepository.findAll().size();

        // Create the Grocerie with an existing ID
        grocerie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrocerieMockMvc.perform(post("/api/groceries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grocerie)))
            .andExpect(status().isBadRequest());

        // Validate the Grocerie in the database
        List<Grocerie> grocerieList = grocerieRepository.findAll();
        assertThat(grocerieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGroceries() throws Exception {
        // Initialize the database
        grocerieRepository.saveAndFlush(grocerie);

        // Get all the grocerieList
        restGrocerieMockMvc.perform(get("/api/groceries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grocerie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGrocerie() throws Exception {
        // Initialize the database
        grocerieRepository.saveAndFlush(grocerie);

        // Get the grocerie
        restGrocerieMockMvc.perform(get("/api/groceries/{id}", grocerie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grocerie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrocerie() throws Exception {
        // Get the grocerie
        restGrocerieMockMvc.perform(get("/api/groceries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrocerie() throws Exception {
        // Initialize the database
        grocerieRepository.saveAndFlush(grocerie);
        int databaseSizeBeforeUpdate = grocerieRepository.findAll().size();

        // Update the grocerie
        Grocerie updatedGrocerie = grocerieRepository.findOne(grocerie.getId());
        // Disconnect from session so that the updates on updatedGrocerie are not directly saved in db
        em.detach(updatedGrocerie);
        updatedGrocerie
            .name(UPDATED_NAME);

        restGrocerieMockMvc.perform(put("/api/groceries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrocerie)))
            .andExpect(status().isOk());

        // Validate the Grocerie in the database
        List<Grocerie> grocerieList = grocerieRepository.findAll();
        assertThat(grocerieList).hasSize(databaseSizeBeforeUpdate);
        Grocerie testGrocerie = grocerieList.get(grocerieList.size() - 1);
        assertThat(testGrocerie.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingGrocerie() throws Exception {
        int databaseSizeBeforeUpdate = grocerieRepository.findAll().size();

        // Create the Grocerie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrocerieMockMvc.perform(put("/api/groceries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grocerie)))
            .andExpect(status().isCreated());

        // Validate the Grocerie in the database
        List<Grocerie> grocerieList = grocerieRepository.findAll();
        assertThat(grocerieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrocerie() throws Exception {
        // Initialize the database
        grocerieRepository.saveAndFlush(grocerie);
        int databaseSizeBeforeDelete = grocerieRepository.findAll().size();

        // Get the grocerie
        restGrocerieMockMvc.perform(delete("/api/groceries/{id}", grocerie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Grocerie> grocerieList = grocerieRepository.findAll();
        assertThat(grocerieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grocerie.class);
        Grocerie grocerie1 = new Grocerie();
        grocerie1.setId(1L);
        Grocerie grocerie2 = new Grocerie();
        grocerie2.setId(grocerie1.getId());
        assertThat(grocerie1).isEqualTo(grocerie2);
        grocerie2.setId(2L);
        assertThat(grocerie1).isNotEqualTo(grocerie2);
        grocerie1.setId(null);
        assertThat(grocerie1).isNotEqualTo(grocerie2);
    }
}
