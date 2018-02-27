package com.mac.nutritio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @OneToOne
    @JoinColumn(unique = true)
    private Stock stock;

    @OneToOne
    @JoinColumn(unique = true)
    private Grocerie grocerie;

    @OneToOne
    @JoinColumn(unique = true)
    private BlackList blackList;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Meal> meals = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Goal> goals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public Person firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Person lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Person birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public Stock getStock() {
        return stock;
    }

    public Person stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Grocerie getGrocerie() {
        return grocerie;
    }

    public Person grocerie(Grocerie grocerie) {
        this.grocerie = grocerie;
        return this;
    }

    public void setGrocerie(Grocerie grocerie) {
        this.grocerie = grocerie;
    }

    public BlackList getBlackList() {
        return blackList;
    }

    public Person blackList(BlackList blackList) {
        this.blackList = blackList;
        return this;
    }

    public void setBlackList(BlackList blackList) {
        this.blackList = blackList;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public Person meals(Set<Meal> meals) {
        this.meals = meals;
        return this;
    }

    public Person addMeal(Meal meal) {
        this.meals.add(meal);
        meal.setPerson(this);
        return this;
    }

    public Person removeMeal(Meal meal) {
        this.meals.remove(meal);
        meal.setPerson(null);
        return this;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Set<Goal> getGoals() {
        return goals;
    }

    public Person goals(Set<Goal> goals) {
        this.goals = goals;
        return this;
    }

    public Person addGoal(Goal goal) {
        this.goals.add(goal);
        goal.setPerson(this);
        return this;
    }

    public Person removeGoal(Goal goal) {
        this.goals.remove(goal);
        goal.setPerson(null);
        return this;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
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
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}
