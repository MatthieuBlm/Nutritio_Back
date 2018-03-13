package com.mac.nutritio.service;

import com.mac.nutritio.domain.*;
import com.mac.nutritio.repository.MealRepository;
import com.mac.nutritio.repository.PersonRepository;
import com.mac.nutritio.repository.RecipeRepository;
import com.mac.nutritio.repository.StockRepository;
import com.mac.nutritio.web.rest.util.Intake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    private final MealRepository mealRepository;

    private final RecipeRepository recipeRepository;

    private final StockRepository stockRepository;

    public PersonService(PersonRepository personRepository, RecipeRepository recipeRepository, MealRepository mealRepository, StockRepository stockRepository){
        this.personRepository = personRepository;
        this.recipeRepository = recipeRepository;
        this.mealRepository = mealRepository;
        this.stockRepository = stockRepository;
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

    public Map<Long, Long> getMealSuggestions(Long id) {
        log.debug("Get meals suggesitons for : {}", id);
        List<Meal> suggestion = new ArrayList<>();

        // Récuperer Intakes
        Intake intake = this.getIntake(id);
        // Récupérer toute les recettes
        List<Recipe> recipes = recipeRepository.findAll();
        // Récupérer le stock de la personne
        Stock stock = stockRepository.findOneOfWithEagerRelationships(id);

        // Calculer la distance entre chaque plat et les intakes
        Map<Long, Long> distanceIntakes = this.calculateIntakeDistance(recipes, intake);

        // Calculer la distance entre chaque plat et le stock
        Map<Long, Long> distanceStock = this.calculateStockDistance(recipes, stock);

        // Faire la somme des distance
        Map<Long, Long>  distanceSum = new HashMap<>();
        for (Map.Entry<Long, Long> entry : distanceIntakes.entrySet()) {
            long sum = entry.getValue();
            sum += distanceStock.get(entry.getKey());
            distanceSum.put(entry.getKey(), sum);
        }

        return distanceSum;
    }

    private Map<Long, Long> calculateIntakeDistance(List<Recipe> recipes, Intake personIntake) {
        Map<Long, Long> result = new HashMap<>();

        for (Recipe r : recipes) {
            Intake recipeValue = new Intake();
            for (IngredientEntry entry : r.getIngredientEntries()) {
                recipeValue.addCarbohydrate(entry.getAmount() * entry.getIngredient().getCarbohydrate() / 100);
                recipeValue.addSaturatedFat(entry.getAmount() * entry.getIngredient().getSaturatedFat() / 100);
                recipeValue.addProtein(entry.getAmount() * entry.getIngredient().getProtein() / 100);
                recipeValue.addEnergy(entry.getAmount() * entry.getIngredient().getEnergy() / 100);
                recipeValue.addFibre(entry.getAmount() * entry.getIngredient().getFibre() / 100);
                recipeValue.addSugar(entry.getAmount() * entry.getIngredient().getSugar() / 100);
                recipeValue.addFat(entry.getAmount() * entry.getIngredient().getFat() / 100);
            }

            long distance = 0;

            distance += Math.abs(recipeValue.getCarbohydrate() - personIntake.getCarbohydrate());
            distance += Math.abs(recipeValue.getSaturatedFat() - personIntake.getSaturatedFat());
            distance += Math.abs(recipeValue.getProtein() - personIntake.getProtein());
            distance += Math.abs(recipeValue.getEnergy() - personIntake.getEnergy());
            distance += Math.abs(recipeValue.getSugar() - personIntake.getSugar());
            distance += Math.abs(recipeValue.getFat() - personIntake.getFat());

            result.put(r.getId(), distance);
        }

        return result;
    }

    private Map<Long, Long> calculateStockDistance(List<Recipe> recipes, Stock stock) {
        Map<Long, Long> result = new HashMap<>();

        // Pour chaque recette
        for (Recipe r : recipes) {
            long distance = 0;      // Distance entre la recette et les stocks disponnible

            // Pour chaque ingredientEntry de la recette
            for (IngredientEntry entry : r.getIngredientEntries()) {
                Ingredient currentIngredient = entry.getIngredient();
                boolean ingredientFound = false;

                for (IngredientEntry stockEntry : stock.getIngredientEntries()) {
                    // Si l'ingredient se trouve dans le stock
                    if(currentIngredient.equals(stockEntry.getIngredient())) {
                        ingredientFound = true;
                        // Si le stock possède moins qu'il n'en faut
                        if(stockEntry.getAmount() < entry.getAmount()) {
                            distance += entry.getAmount() - stockEntry.getAmount();
                        }
                        break; // Ingredient évalué a ce stade, on passe au suivant
                    }
                }

                // Si l'ingredient n'est pas dans le stock, double pénalité
                if(!ingredientFound){
                    distance += entry.getAmount() * 2;
                }
            }

            result.put(r.getId(), distance);
        }

        return result;
    }
}
