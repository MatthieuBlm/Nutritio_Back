package com.mac.nutritio.repository;

import com.mac.nutritio.domain.Stock;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("select distinct stock from Stock stock left join fetch stock.ingredientEntries")
    List<Stock> findAllWithEagerRelationships();

    @Query("select stock from Stock stock left join fetch stock.ingredientEntries where stock.id =:id")
    Stock findOneWithEagerRelationships(@Param("id") Long id);

}
