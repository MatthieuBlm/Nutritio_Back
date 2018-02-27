package com.mac.nutritio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BlackList.
 */
@Entity
@Table(name = "black_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlackList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "black_list_ingredient_entry",
               joinColumns = @JoinColumn(name="black_lists_id", referencedColumnName="id"),
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

    public BlackList name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IngredientEntry> getIngredientEntries() {
        return ingredientEntries;
    }

    public BlackList ingredientEntries(Set<IngredientEntry> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
        return this;
    }

    public BlackList addIngredientEntry(IngredientEntry ingredientEntry) {
        this.ingredientEntries.add(ingredientEntry);
        ingredientEntry.getBlackLists().add(this);
        return this;
    }

    public BlackList removeIngredientEntry(IngredientEntry ingredientEntry) {
        this.ingredientEntries.remove(ingredientEntry);
        ingredientEntry.getBlackLists().remove(this);
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
        BlackList blackList = (BlackList) o;
        if (blackList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blackList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BlackList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
