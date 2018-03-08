package com.mac.nutritio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mac.nutritio.domain.Meal;

import com.mac.nutritio.repository.MealRepository;
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
 * REST controller for managing Meal.
 */
@RestController
@RequestMapping("/api")
public class MealResource {

    private final Logger log = LoggerFactory.getLogger(MealResource.class);

    private static final String ENTITY_NAME = "meal";

    private final MealRepository mealRepository;

    public MealResource(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    /**
     * POST  /meals : Create a new meal.
     *
     * @param meal the meal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meal, or with status 400 (Bad Request) if the meal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meals")
    @Timed
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) throws URISyntaxException {
        log.debug("REST request to save Meal : {}", meal);
        if (meal.getId() != null) {
            throw new BadRequestAlertException("A new meal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Meal result = mealRepository.save(meal);
        return ResponseEntity.created(new URI("/api/meals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meals : Updates an existing meal.
     *
     * @param meal the meal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meal,
     * or with status 400 (Bad Request) if the meal is not valid,
     * or with status 500 (Internal Server Error) if the meal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meals")
    @Timed
    public ResponseEntity<Meal> updateMeal(@RequestBody Meal meal) throws URISyntaxException {
        log.debug("REST request to update Meal : {}", meal);
        if (meal.getId() == null) {
            return createMeal(meal);
        }
        Meal result = mealRepository.save(meal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meals : get all the meals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of meals in body
     */
    @GetMapping("/meals")
    @Timed
    public List<Meal> getAllMeals() {
        log.debug("REST request to get all Meals");
        return mealRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /meals/:id : get the "id" meal.
     *
     * @param id the id of the meal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meal, or with status 404 (Not Found)
     */
    @GetMapping("/meals/{id}")
    @Timed
    public ResponseEntity<Meal> getMeal(@PathVariable Long id) {
        log.debug("REST request to get Meal : {}", id);
        Meal meal = mealRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meal));
    }

    /**
     * GET  /meals/:email : get the "email"'s person meals.
     *
     * @param email the email of the meals to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meal, or with status 404 (Not Found)
     */
    @GetMapping("/meals/{email}")
    @Timed
    public List<Meal> getAllMealOf(@PathVariable Long id) {
        log.debug("REST request to get all Meals of : {}", id);
        return mealRepository.findAllWithEagerRelationships(id);
    }

    /**
     * DELETE  /meals/:id : delete the "id" meal.
     *
     * @param id the id of the meal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meals/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        log.debug("REST request to delete Meal : {}", id);
        mealRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
