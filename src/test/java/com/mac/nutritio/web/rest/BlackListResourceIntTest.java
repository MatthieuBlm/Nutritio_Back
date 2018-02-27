package com.mac.nutritio.web.rest;

import com.mac.nutritio.NutritioBackApp;

import com.mac.nutritio.domain.BlackList;
import com.mac.nutritio.repository.BlackListRepository;
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
 * Test class for the BlackListResource REST controller.
 *
 * @see BlackListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NutritioBackApp.class)
public class BlackListResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBlackListMockMvc;

    private BlackList blackList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlackListResource blackListResource = new BlackListResource(blackListRepository);
        this.restBlackListMockMvc = MockMvcBuilders.standaloneSetup(blackListResource)
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
    public static BlackList createEntity(EntityManager em) {
        BlackList blackList = new BlackList()
            .name(DEFAULT_NAME);
        return blackList;
    }

    @Before
    public void initTest() {
        blackList = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlackList() throws Exception {
        int databaseSizeBeforeCreate = blackListRepository.findAll().size();

        // Create the BlackList
        restBlackListMockMvc.perform(post("/api/black-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blackList)))
            .andExpect(status().isCreated());

        // Validate the BlackList in the database
        List<BlackList> blackListList = blackListRepository.findAll();
        assertThat(blackListList).hasSize(databaseSizeBeforeCreate + 1);
        BlackList testBlackList = blackListList.get(blackListList.size() - 1);
        assertThat(testBlackList.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBlackListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blackListRepository.findAll().size();

        // Create the BlackList with an existing ID
        blackList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlackListMockMvc.perform(post("/api/black-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blackList)))
            .andExpect(status().isBadRequest());

        // Validate the BlackList in the database
        List<BlackList> blackListList = blackListRepository.findAll();
        assertThat(blackListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBlackLists() throws Exception {
        // Initialize the database
        blackListRepository.saveAndFlush(blackList);

        // Get all the blackListList
        restBlackListMockMvc.perform(get("/api/black-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blackList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBlackList() throws Exception {
        // Initialize the database
        blackListRepository.saveAndFlush(blackList);

        // Get the blackList
        restBlackListMockMvc.perform(get("/api/black-lists/{id}", blackList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blackList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBlackList() throws Exception {
        // Get the blackList
        restBlackListMockMvc.perform(get("/api/black-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlackList() throws Exception {
        // Initialize the database
        blackListRepository.saveAndFlush(blackList);
        int databaseSizeBeforeUpdate = blackListRepository.findAll().size();

        // Update the blackList
        BlackList updatedBlackList = blackListRepository.findOne(blackList.getId());
        // Disconnect from session so that the updates on updatedBlackList are not directly saved in db
        em.detach(updatedBlackList);
        updatedBlackList
            .name(UPDATED_NAME);

        restBlackListMockMvc.perform(put("/api/black-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlackList)))
            .andExpect(status().isOk());

        // Validate the BlackList in the database
        List<BlackList> blackListList = blackListRepository.findAll();
        assertThat(blackListList).hasSize(databaseSizeBeforeUpdate);
        BlackList testBlackList = blackListList.get(blackListList.size() - 1);
        assertThat(testBlackList.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBlackList() throws Exception {
        int databaseSizeBeforeUpdate = blackListRepository.findAll().size();

        // Create the BlackList

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBlackListMockMvc.perform(put("/api/black-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blackList)))
            .andExpect(status().isCreated());

        // Validate the BlackList in the database
        List<BlackList> blackListList = blackListRepository.findAll();
        assertThat(blackListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBlackList() throws Exception {
        // Initialize the database
        blackListRepository.saveAndFlush(blackList);
        int databaseSizeBeforeDelete = blackListRepository.findAll().size();

        // Get the blackList
        restBlackListMockMvc.perform(delete("/api/black-lists/{id}", blackList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BlackList> blackListList = blackListRepository.findAll();
        assertThat(blackListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlackList.class);
        BlackList blackList1 = new BlackList();
        blackList1.setId(1L);
        BlackList blackList2 = new BlackList();
        blackList2.setId(blackList1.getId());
        assertThat(blackList1).isEqualTo(blackList2);
        blackList2.setId(2L);
        assertThat(blackList1).isNotEqualTo(blackList2);
        blackList1.setId(null);
        assertThat(blackList1).isNotEqualTo(blackList2);
    }
}
