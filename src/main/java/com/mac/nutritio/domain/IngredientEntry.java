package com.mac.nutritio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mac.nutritio.domain.enumeration.Unit;

/**
 * A IngredientEntry.
 */
@Entity
@Table(name = "ingredient_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IngredientEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private Unit unit;

    @ManyToOne
    private Ingredient ingredient;

    @ManyToMany(mappedBy = "ingredientEntries")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @ManyToMany(mappedBy = "ingredientEntries")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Recipe> recipes = new HashSet<>();

    @ManyToMany(mappedBy = "ingredientEntries")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Grocerie> groceries = new HashSet<>();

    @ManyToMany(mappedBy = "ingredientEntries")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BlackList> blackLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public IngredientEntry amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public IngredientEntry unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public IngredientEntry ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public IngredientEntry stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public IngredientEntry addStock(Stock stock) {
        this.stocks.add(stock);
        stock.getIngredientEntries().add(this);
        return this;
    }

    public IngredientEntry removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.getIngredientEntries().remove(this);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public IngredientEntry recipes(Set<Recipe> recipes) {
        this.recipes = recipes;
        return this;
    }

    public IngredientEntry addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
        recipe.getIngredientEntries().add(this);
        return this;
    }

    public IngredientEntry removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe);
        recipe.getIngredientEntries().remove(this);
        return this;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Set<Grocerie> getGroceries() {
        return groceries;
    }

    public IngredientEntry groceries(Set<Grocerie> groceries) {
        this.groceries = groceries;
        return this;
    }

    public IngredientEntry addGrocerie(Grocerie grocerie) {
        this.groceries.add(grocerie);
        grocerie.getIngredientEntries().add(this);
        return this;
    }

    public IngredientEntry removeGrocerie(Grocerie grocerie) {
        this.groceries.remove(grocerie);
        grocerie.getIngredientEntries().remove(this);
        return this;
    }

    public void setGroceries(Set<Grocerie> groceries) {
        this.groceries = groceries;
    }

    public Set<BlackList> getBlackLists() {
        return blackLists;
    }

    public IngredientEntry blackLists(Set<BlackList> blackLists) {
        this.blackLists = blackLists;
        return this;
    }

    public IngredientEntry addBlackList(BlackList blackList) {
        this.blackLists.add(blackList);
        blackList.getIngredientEntries().add(this);
        return this;
    }

    public IngredientEntry removeBlackList(BlackList blackList) {
        this.blackLists.remove(blackList);
        blackList.getIngredientEntries().remove(this);
        return this;
    }

    public void setBlackLists(Set<BlackList> blackLists) {
        this.blackLists = blackLists;
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
        IngredientEntry ingredientEntry = (IngredientEntry) o;
        if (ingredientEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientEntry{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
