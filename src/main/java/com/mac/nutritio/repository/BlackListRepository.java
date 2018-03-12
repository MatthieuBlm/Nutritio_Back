package com.mac.nutritio.repository;

import com.mac.nutritio.domain.BlackList;
import com.mac.nutritio.domain.Stock;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the BlackList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    @Query("select distinct black_list from BlackList black_list left join fetch black_list.ingredientEntries")
    List<BlackList> findAllWithEagerRelationships();

    @Query("select black_list from BlackList black_list left join fetch black_list.ingredientEntries where black_list.id =:id")
    BlackList findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select blackList from Person person where person.id =:id")
    BlackList findOneOfWithEagerRelationships(@Param("id") Long id);
}
