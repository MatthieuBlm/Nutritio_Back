package com.mac.nutritio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "recipe_ingredient_entry",
               joinColumns = @JoinColumn(name="recipes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ingredient_entries_id", referencedColumnName="id"))
    private Set<IngredientEntry> ingredientEntries = new HashSet<>();

    @ManyToMany(mappedBy = "recipes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Meal> meals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Recipe name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Recipe image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<IngredientEntry> getIngredientEntries() {
        return ingredientEntries;
    }

    public Recipe ingredientEntries(Set<IngredientEntry> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
        return this;
    }

    public Recipe addIngredientEntry(IngredientEntry ingredientEntry) {
        this.ingredientEntries.add(ingredientEntry);
        ingredientEntry.getRecipes().add(this);
        return this;
    }

    public Recipe removeIngredientEntry(IngredientEntry ingredientEntry) {
        this.ingredientEntries.remove(ingredientEntry);
        ingredientEntry.getRecipes().remove(this);
        return this;
    }

    public void setIngredientEntries(Set<IngredientEntry> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public Recipe meals(Set<Meal> meals) {
        this.meals = meals;
        return this;
    }

    public Recipe addMeal(Meal meal) {
        this.meals.add(meal);
        meal.getRecipes().add(this);
        return this;
    }

    public Recipe removeMeal(Meal meal) {
        this.meals.remove(meal);
        meal.getRecipes().remove(this);
        return this;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        if (recipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
