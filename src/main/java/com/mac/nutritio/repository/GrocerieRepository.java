package com.mac.nutritio.repository;

import com.mac.nutritio.domain.Grocerie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Grocerie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrocerieRepository extends JpaRepository<Grocerie, Long> {
    @Query("select distinct grocerie from Grocerie grocerie left join fetch grocerie.ingredientEntries")
    List<Grocerie> findAllWithEagerRelationships();

    @Query("select grocerie from Grocerie grocerie left join fetch grocerie.ingredientEntries where grocerie.id =:id")
    Grocerie findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select Grocerie from Person person where person.id =:id")
    Grocerie findOneOfWithEagerRelationships(@Param("id") Long id);

}
