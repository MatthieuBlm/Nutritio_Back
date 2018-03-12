package com.mac.nutritio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Grocerie.
 */
@Entity
@Table(name = "grocerie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grocerie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "grocerie_ingredient_entry",
               joinColumns = @JoinColumn(name="groceries_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ingredient_entries_id", referencedColumnName="id"))
    private Set<IngredientEntry> ingredientEntries = new HashSet<>();

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

    public Grocerie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IngredientEntry> getIngredientEntries() {
        return ingredientEntries;
    }

    public Grocerie ingredientEntries(Set<IngredientEntry> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
        return this;
    }

    public Grocerie addIngredientEntry(IngredientEntry ingredientEntry) {
        this.ingredientEntries.add(ingredientEntry);
        ingredientEntry.getGroceries().add(this);
        return this;
    }

    public Grocerie removeIngredientEntry(IngredientEntry ingredientEntry) {
        this.ingredientEntries.remove(ingredientEntry);
        ingredientEntry.getGroceries().remove(this);
        return this;
    }

    public void setIngredientEntries(Set<IngredientEntry> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
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
        Grocerie grocerie = (Grocerie) o;
        if (grocerie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grocerie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Grocerie{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
