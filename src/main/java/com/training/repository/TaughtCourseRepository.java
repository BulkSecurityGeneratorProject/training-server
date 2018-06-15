package com.training.repository;

import com.training.domain.TaughtCourse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the TaughtCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaughtCourseRepository extends JpaRepository<TaughtCourse, Long> {

    @Query("select taught_course from TaughtCourse taught_course where taught_course.user.login = ?#{principal.username}")
    List<TaughtCourse> findByUserIsCurrentUser();

}
