package com.mac.nutritio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mac.nutritio.domain.Grocerie;

import com.mac.nutritio.repository.GrocerieRepository;
import com.mac.nutritio.web.rest.errors.BadRequestAlertException;
import com.mac.nutritio.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Grocerie.
 */
@RestController
@RequestMapping("/api")
public class GrocerieResource {

    private final Logger log = LoggerFactory.getLogger(GrocerieResource.class);

    private static final String ENTITY_NAME = "grocerie";

    private final GrocerieRepository grocerieRepository;

    public GrocerieResource(GrocerieRepository grocerieRepository) {
        this.grocerieRepository = grocerieRepository;
    }

    /**
     * POST  /groceries : Create a new grocerie.
     *
     * @param grocerie the grocerie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grocerie, or with status 400 (Bad Request) if the grocerie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/groceries")
    @Timed
    public ResponseEntity<Grocerie> createGrocerie(@RequestBody Grocerie grocerie) throws URISyntaxException {
        log.debug("REST request to save Grocerie : {}", grocerie);
        if (grocerie.getId() != null) {
            throw new BadRequestAlertException("A new grocerie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grocerie result = grocerieRepository.save(grocerie);
        return ResponseEntity.created(new URI("/api/groceries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /groceries : Updates an existing grocerie.
     *
     * @param grocerie the grocerie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grocerie,
     * or with status 400 (Bad Request) if the grocerie is not valid,
     * or with status 500 (Internal Server Error) if the grocerie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/groceries")
    @Timed
    public ResponseEntity<Grocerie> updateGrocerie(@RequestBody Grocerie grocerie) throws URISyntaxException {
        log.debug("REST request to update Grocerie : {}", grocerie);
        if (grocerie.getId() == null) {
            return createGrocerie(grocerie);
        }
        Grocerie result = grocerieRepository.save(grocerie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grocerie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /groceries : get all the groceries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groceries in body
     */
    @GetMapping("/groceries")
    @Timed
    public List<Grocerie> getAllGroceries() {
        log.debug("REST request to get all Groceries");
        return grocerieRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /groceries/:id : get the "id" grocerie.
     *
     * @param id the id of the grocerie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grocerie, or with status 404 (Not Found)
     */
    @GetMapping("/groceries/{id}")
    @Timed
    public ResponseEntity<Grocerie> getGrocerie(@PathVariable Long id) {
        log.debug("REST request to get Grocerie : {}", id);
        Grocerie grocerie = grocerieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grocerie));
    }

    /**
     * DELETE  /groceries/:id : delete the "id" grocerie.
     *
     * @param id the id of the grocerie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/groceries/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrocerie(@PathVariable Long id) {
        log.debug("REST request to delete Grocerie : {}", id);
        grocerieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
