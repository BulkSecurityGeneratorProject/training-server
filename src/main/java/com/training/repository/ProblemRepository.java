package com.training.repository;

import com.training.domain.Problem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Problem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("select problem from Problem problem where problem.user.login = ?#{principal.username}")
    List<Problem> findByUserIsCurrentUser();

}
