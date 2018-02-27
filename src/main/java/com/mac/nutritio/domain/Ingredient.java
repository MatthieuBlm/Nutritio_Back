package com.mac.nutritio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mac.nutritio.domain.enumeration.Category;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand")
    private String brand;

    @NotNull
    @Column(name = "energy", nullable = false)
    private Long energy;

    @NotNull
    @Column(name = "protein", nullable = false)
    private Long protein;

    @NotNull
    @Column(name = "carbohydrate", nullable = false)
    private Long carbohydrate;

    @NotNull
    @Column(name = "fat", nullable = false)
    private Long fat;

    @NotNull
    @Column(name = "sugar", nullable = false)
    private Long sugar;

    @NotNull
    @Column(name = "saturated_fat", nullable = false)
    private Long saturatedFat;

    @NotNull
    @Column(name = "fibre", nullable = false)
    private Long fibre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

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

    public Ingredient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public Ingredient brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getEnergy() {
        return energy;
    }

    public Ingredient energy(Long energy) {
        this.energy = energy;
        return this;
    }

    public void setEnergy(Long energy) {
        this.energy = energy;
    }

    public Long getProtein() {
        return protein;
    }

    public Ingredient protein(Long protein) {
        this.protein = protein;
        return this;
    }

    public void setProtein(Long protein) {
        this.protein = protein;
    }

    public Long getCarbohydrate() {
        return carbohydrate;
    }

    public Ingredient carbohydrate(Long carbohydrate) {
        this.carbohydrate = carbohydrate;
        return this;
    }

    public void setCarbohydrate(Long carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Long getFat() {
        return fat;
    }

    public Ingredient fat(Long fat) {
        this.fat = fat;
        return this;
    }

    public void setFat(Long fat) {
        this.fat = fat;
    }

    public Long getSugar() {
        return sugar;
    }

    public Ingredient sugar(Long sugar) {
        this.sugar = sugar;
        return this;
    }

    public void setSugar(Long sugar) {
        this.sugar = sugar;
    }

    public Long getSaturatedFat() {
        return saturatedFat;
    }

    public Ingredient saturatedFat(Long saturatedFat) {
        this.saturatedFat = saturatedFat;
        return this;
    }

    public void setSaturatedFat(Long saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Long getFibre() {
        return fibre;
    }

    public Ingredient fibre(Long fibre) {
        this.fibre = fibre;
        return this;
    }

    public void setFibre(Long fibre) {
        this.fibre = fibre;
    }

    public Category getCategory() {
        return category;
    }

    public Ingredient category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Ingredient ingredient = (Ingredient) o;
        if (ingredient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", energy=" + getEnergy() +
            ", protein=" + getProtein() +
            ", carbohydrate=" + getCarbohydrate() +
            ", fat=" + getFat() +
            ", sugar=" + getSugar() +
            ", saturatedFat=" + getSaturatedFat() +
            ", fibre=" + getFibre() +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
