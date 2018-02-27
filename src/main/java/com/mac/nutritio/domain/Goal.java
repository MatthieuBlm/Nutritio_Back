package com.mac.nutritio.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Goal.
 */
@Entity
@Table(name = "goal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "energy")
    private Long energy;

    @Column(name = "protein")
    private Long protein;

    @Column(name = "carbohydrate")
    private Long carbohydrate;

    @Column(name = "fat")
    private Long fat;

    @Column(name = "sugar")
    private Long sugar;

    @Column(name = "saturated_fat")
    private Long saturatedFat;

    @Column(name = "fibre")
    private Long fibre;

    @ManyToOne
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Goal date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getEnergy() {
        return energy;
    }

    public Goal energy(Long energy) {
        this.energy = energy;
        return this;
    }

    public void setEnergy(Long energy) {
        this.energy = energy;
    }

    public Long getProtein() {
        return protein;
    }

    public Goal protein(Long protein) {
        this.protein = protein;
        return this;
    }

    public void setProtein(Long protein) {
        this.protein = protein;
    }

    public Long getCarbohydrate() {
        return carbohydrate;
    }

    public Goal carbohydrate(Long carbohydrate) {
        this.carbohydrate = carbohydrate;
        return this;
    }

    public void setCarbohydrate(Long carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Long getFat() {
        return fat;
    }

    public Goal fat(Long fat) {
        this.fat = fat;
        return this;
    }

    public void setFat(Long fat) {
        this.fat = fat;
    }

    public Long getSugar() {
        return sugar;
    }

    public Goal sugar(Long sugar) {
        this.sugar = sugar;
        return this;
    }

    public void setSugar(Long sugar) {
        this.sugar = sugar;
    }

    public Long getSaturatedFat() {
        return saturatedFat;
    }

    public Goal saturatedFat(Long saturatedFat) {
        this.saturatedFat = saturatedFat;
        return this;
    }

    public void setSaturatedFat(Long saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Long getFibre() {
        return fibre;
    }

    public Goal fibre(Long fibre) {
        this.fibre = fibre;
        return this;
    }

    public void setFibre(Long fibre) {
        this.fibre = fibre;
    }

    public Person getPerson() {
        return person;
    }

    public Goal person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        Goal goal = (Goal) o;
        if (goal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), goal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Goal{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", energy=" + getEnergy() +
            ", protein=" + getProtein() +
            ", carbohydrate=" + getCarbohydrate() +
            ", fat=" + getFat() +
            ", sugar=" + getSugar() +
            ", saturatedFat=" + getSaturatedFat() +
            ", fibre=" + getFibre() +
            "}";
    }
}
