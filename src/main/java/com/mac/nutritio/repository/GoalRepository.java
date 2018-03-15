package com.mac.nutritio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mac.nutritio.domain.Goal;


/**
 * Spring Data JPA repository for the Goal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
	
	@Query("select goal from Goal goal left join fetch goal.person where goal.person.id =:id")
    Goal findOneWithEagerRelationships(@Param("id") Long id);
}
