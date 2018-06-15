package com.training.repository;

import com.training.domain.CareerPath;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CareerPath entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerPathRepository extends JpaRepository<CareerPath, Long> {

}
