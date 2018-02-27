package com.mac.nutritio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mac.nutritio.domain.IngredientEntry;

import com.mac.nutritio.repository.IngredientEntryRepository;
import com.mac.nutritio.web.rest.errors.BadRequestAlertException;
import com.mac.nutritio.web.rest.util.HeaderUtil;
import com.mac.nutritio.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IngredientEntry.
 */
@RestController
@RequestMapping("/api")
public class IngredientEntryResource {

    private final Logger log = LoggerFactory.getLogger(IngredientEntryResource.class);

    private static final String ENTITY_NAME = "ingredientEntry";

    private final IngredientEntryRepository ingredientEntryRepository;

    public IngredientEntryResource(IngredientEntryRepository ingredientEntryRepository) {
        this.ingredientEntryRepository = ingredientEntryRepository;
    }

    /**
     * POST  /ingredient-entries : Create a new ingredientEntry.
     *
     * @param ingredientEntry the ingredientEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ingredientEntry, or with status 400 (Bad Request) if the ingredientEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ingredient-entries")
    @Timed
    public ResponseEntity<IngredientEntry> createIngredientEntry(@Valid @RequestBody IngredientEntry ingredientEntry) throws URISyntaxException {
        log.debug("REST request to save IngredientEntry : {}", ingredientEntry);
        if (ingredientEntry.getId() != null) {
            throw new BadRequestAlertException("A new ingredientEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientEntry result = ingredientEntryRepository.save(ingredientEntry);
        return ResponseEntity.created(new URI("/api/ingredient-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ingredient-entries : Updates an existing ingredientEntry.
     *
     * @param ingredientEntry the ingredientEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ingredientEntry,
     * or with status 400 (Bad Request) if the ingredientEntry is not valid,
     * or with status 500 (Internal Server Error) if the ingredientEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ingredient-entries")
    @Timed
    public ResponseEntity<IngredientEntry> updateIngredientEntry(@Valid @RequestBody IngredientEntry ingredientEntry) throws URISyntaxException {
        log.debug("REST request to update IngredientEntry : {}", ingredientEntry);
        if (ingredientEntry.getId() == null) {
            return createIngredientEntry(ingredientEntry);
        }
        IngredientEntry result = ingredientEntryRepository.save(ingredientEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ingredientEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ingredient-entries : get all the ingredientEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ingredientEntries in body
     */
    @GetMapping("/ingredient-entries")
    @Timed
    public ResponseEntity<List<IngredientEntry>> getAllIngredientEntries(Pageable pageable) {
        log.debug("REST request to get a page of IngredientEntries");
        Page<IngredientEntry> page = ingredientEntryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ingredient-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ingredient-entries/:id : get the "id" ingredientEntry.
     *
     * @param id the id of the ingredientEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ingredientEntry, or with status 404 (Not Found)
     */
    @GetMapping("/ingredient-entries/{id}")
    @Timed
    public ResponseEntity<IngredientEntry> getIngredientEntry(@PathVariable Long id) {
        log.debug("REST request to get IngredientEntry : {}", id);
        IngredientEntry ingredientEntry = ingredientEntryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ingredientEntry));
    }

    /**
     * DELETE  /ingredient-entries/:id : delete the "id" ingredientEntry.
     *
     * @param id the id of the ingredientEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ingredient-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteIngredientEntry(@PathVariable Long id) {
        log.debug("REST request to delete IngredientEntry : {}", id);
        ingredientEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
