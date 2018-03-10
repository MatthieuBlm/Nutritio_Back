package com.mac.nutritio.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mac.nutritio.domain.Meal;

/**
 * Spring Data JPA repository for the Meal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    @Query("select distinct meal from Meal meal left join fetch meal.recipes")
    List<Meal> findAllWithEagerRelationships();

    @Query("select meal from Meal meal left join fetch meal.recipes where meal.id =:id")
    Meal findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select meal from Meal meal left join fetch meal.recipes where meal.person.id =:id")
    List<Meal> findAllWithEagerRelationships(@Param("id") Long id);

    @Query("select meal from Meal meal left join fetch meal.recipes where meal.date between :dateDeb and :dateFin and meal.person.id =:id")
    List<Meal> findAllByDateBetweenWithEagerRelationships(@Param("id") Long id, @Param("dateDeb") Date dateDeb, @Param("dateFin") Date dateFin);

}
