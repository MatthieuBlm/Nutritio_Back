package com.mac.nutritio.repository;

import com.mac.nutritio.domain.IngredientEntry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IngredientEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientEntryRepository extends JpaRepository<IngredientEntry, Long> {

}
