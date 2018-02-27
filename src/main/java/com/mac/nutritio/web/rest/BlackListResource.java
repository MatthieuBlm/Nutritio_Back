package com.mac.nutritio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mac.nutritio.domain.BlackList;

import com.mac.nutritio.repository.BlackListRepository;
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
 * REST controller for managing BlackList.
 */
@RestController
@RequestMapping("/api")
public class BlackListResource {

    private final Logger log = LoggerFactory.getLogger(BlackListResource.class);

    private static final String ENTITY_NAME = "blackList";

    private final BlackListRepository blackListRepository;

    public BlackListResource(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    /**
     * POST  /black-lists : Create a new blackList.
     *
     * @param blackList the blackList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blackList, or with status 400 (Bad Request) if the blackList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/black-lists")
    @Timed
    public ResponseEntity<BlackList> createBlackList(@RequestBody BlackList blackList) throws URISyntaxException {
        log.debug("REST request to save BlackList : {}", blackList);
        if (blackList.getId() != null) {
            throw new BadRequestAlertException("A new blackList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlackList result = blackListRepository.save(blackList);
        return ResponseEntity.created(new URI("/api/black-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /black-lists : Updates an existing blackList.
     *
     * @param blackList the blackList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blackList,
     * or with status 400 (Bad Request) if the blackList is not valid,
     * or with status 500 (Internal Server Error) if the blackList couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/black-lists")
    @Timed
    public ResponseEntity<BlackList> updateBlackList(@RequestBody BlackList blackList) throws URISyntaxException {
        log.debug("REST request to update BlackList : {}", blackList);
        if (blackList.getId() == null) {
            return createBlackList(blackList);
        }
        BlackList result = blackListRepository.save(blackList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blackList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /black-lists : get all the blackLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blackLists in body
     */
    @GetMapping("/black-lists")
    @Timed
    public List<BlackList> getAllBlackLists() {
        log.debug("REST request to get all BlackLists");
        return blackListRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /black-lists/:id : get the "id" blackList.
     *
     * @param id the id of the blackList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blackList, or with status 404 (Not Found)
     */
    @GetMapping("/black-lists/{id}")
    @Timed
    public ResponseEntity<BlackList> getBlackList(@PathVariable Long id) {
        log.debug("REST request to get BlackList : {}", id);
        BlackList blackList = blackListRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(blackList));
    }

    /**
     * DELETE  /black-lists/:id : delete the "id" blackList.
     *
     * @param id the id of the blackList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/black-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlackList(@PathVariable Long id) {
        log.debug("REST request to delete BlackList : {}", id);
        blackListRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
