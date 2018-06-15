package com.training.repository;

import com.training.domain.SectionContent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SectionContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectionContentRepository extends JpaRepository<SectionContent, Long> {

}
