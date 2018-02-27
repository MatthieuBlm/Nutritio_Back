package com.mac.nutritio.repository;

import com.mac.nutritio.domain.Recipe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Recipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select distinct recipe from Recipe recipe left join fetch recipe.ingredientEntries")
    List<Recipe> findAllWithEagerRelationships();

    @Query("select recipe from Recipe recipe left join fetch recipe.ingredientEntries where recipe.id =:id")
    Recipe findOneWithEagerRelationships(@Param("id") Long id);

}
