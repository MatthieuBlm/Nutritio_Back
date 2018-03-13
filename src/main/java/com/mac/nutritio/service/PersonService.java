package com.mac.nutritio.service;

import com.mac.nutritio.domain.IngredientEntry;
import com.mac.nutritio.domain.Meal;
import com.mac.nutritio.domain.Recipe;
import com.mac.nutritio.repository.MealRepository;
import com.mac.nutritio.repository.PersonRepository;
import com.mac.nutritio.web.rest.util.Intake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    private final MealRepository mealRepository;

    public PersonService(PersonRepository personRepository, MealRepository mealRepository){
        this.personRepository = personRepository;
        this.mealRepository = mealRepository;
    }

    public Intake getIntake(long id){
        log.debug("Get Intakes for id : {}", id);
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
        Intake intake = new Intake();

        for (Meal meal : meals) {
            for (Recipe recipe : meal.getRecipes()) {
                for (IngredientEntry ingredientEntry : recipe.getIngredientEntries()) {
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

        return intake;
    }
}
