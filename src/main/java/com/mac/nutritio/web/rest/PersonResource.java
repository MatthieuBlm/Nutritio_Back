package com.mac.nutritio.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mac.nutritio.domain.*;

import com.mac.nutritio.repository.MealRepository;
import com.mac.nutritio.repository.PersonRepository;
import com.mac.nutritio.web.rest.errors.BadRequestAlertException;
import com.mac.nutritio.web.rest.util.HeaderUtil;
import com.mac.nutritio.web.rest.util.Intake;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Person.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    private final PersonRepository personRepository;

    @Autowired
    private MealRepository mealRepository;

    public PersonResource(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * POST  /people : Create a new person.
     *
     * @param person the person to create
     * @return the ResponseEntity with status 201 (Created) and with body the new person, or with status 400 (Bad Request) if the person has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people")
    @Timed
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Person result = personRepository.save(person);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing person.
     *
     * @param person the person to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated person,
     * or with status 400 (Bad Request) if the person is not valid,
     * or with status 500 (Internal Server Error) if the person couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people")
    @Timed
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to update Person : {}", person);
        if (person.getId() == null) {
            return createPerson(person);
        }
        Person result = personRepository.save(person);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, person.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people : get all the people.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of people in body
     */
    @GetMapping("/people")
    @Timed
    public List<Person> getAllPeople() {
        log.debug("REST request to get all People");
        return personRepository.findAll();
        }

    /**
     * GET  /people/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the person, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}")
    @Timed
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(person));
    }

    /**
     * GET  /people/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the Intakes with status 200 (OK) and with body the person, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}/getTodayIntakes")
    @Timed
    public ResponseEntity<Intake> getPersonTodayIntakes(@PathVariable Long id) {
        log.debug("REST request to get Person today intakes : {}", id);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        ZonedDateTime deb = calendar.toInstant().atZone(ZoneId.of("Europe/Paris"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        ZonedDateTime fin = calendar.toInstant().atZone(ZoneId.of("Europe/Paris"));

        List<Meal> meals = mealRepository.findAllByDateBetweenWithEagerRelationships(id, deb, fin);
        log.debug("########## deb : {}, fin : {}, meals.size() : {}", deb, fin, meals.size());
        Intake intake = new Intake();

        for (Meal meal : meals) {
            for (Recipe recipe : meal.getRecipes()) {
                for (IngredientEntry ingredientEntry : recipe.getIngredientEntries()) {
                    log.debug(">>>>>> {}", ingredientEntry.getIngredient());
                    intake.addProtein(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getProtein() / 100);
                    intake.addCarbohydrate(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getCarbohydrate() / 100);
                    intake.addSugar(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getSugar() / 100);
                    intake.addFat(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getFat() / 100);
                    intake.addSaturatedFat(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getSaturatedFat() / 100);
                    intake.addFibre(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getFibre() / 100);
                    intake.addEnergy(ingredientEntry.getAmount() * ingredientEntry.getIngredient().getEnergy() / 100);
                }
            }
        }
         log.debug("FINAL INTAKE : {}", intake);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(intake));
    }

    /**
     * DELETE  /people/:id : delete the "id" person.
     *
     * @param id the id of the person to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people/{id}")
    @Timed
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
