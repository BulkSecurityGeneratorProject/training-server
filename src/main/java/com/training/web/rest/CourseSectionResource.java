package com.training.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.training.service.CourseSectionService;
import com.training.web.rest.errors.BadRequestAlertException;
import com.training.web.rest.util.HeaderUtil;
import com.training.web.rest.util.PaginationUtil;
import com.training.service.dto.CourseSectionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CourseSection.
 */
@RestController
@RequestMapping("/api")
public class CourseSectionResource {

    private final Logger log = LoggerFactory.getLogger(CourseSectionResource.class);

    private static final String ENTITY_NAME = "courseSection";

    private final CourseSectionService courseSectionService;

    public CourseSectionResource(CourseSectionService courseSectionService) {
        this.courseSectionService = courseSectionService;
    }

    /**
     * POST  /course-sections : Create a new courseSection.
     *
     * @param courseSectionDTO the courseSectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courseSectionDTO, or with status 400 (Bad Request) if the courseSection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/course-sections")
    @Timed
    public ResponseEntity<CourseSectionDTO> createCourseSection(@RequestBody CourseSectionDTO courseSectionDTO) throws URISyntaxException {
        log.debug("REST request to save CourseSection : {}", courseSectionDTO);
        if (courseSectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseSection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseSectionDTO result = courseSectionService.save(courseSectionDTO);
        return ResponseEntity.created(new URI("/api/course-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /course-sections : Updates an existing courseSection.
     *
     * @param courseSectionDTO the courseSectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courseSectionDTO,
     * or with status 400 (Bad Request) if the courseSectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the courseSectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/course-sections")
    @Timed
    public ResponseEntity<CourseSectionDTO> updateCourseSection(@RequestBody CourseSectionDTO courseSectionDTO) throws URISyntaxException {
        log.debug("REST request to update CourseSection : {}", courseSectionDTO);
        if (courseSectionDTO.getId() == null) {
            return createCourseSection(courseSectionDTO);
        }
        CourseSectionDTO result = courseSectionService.save(courseSectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courseSectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /course-sections : get all the courseSections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courseSections in body
     */
    @GetMapping("/course-sections")
    @Timed
    public ResponseEntity<List<CourseSectionDTO>> getAllCourseSections(Pageable pageable) {
        log.debug("REST request to get a page of CourseSections");
        Page<CourseSectionDTO> page = courseSectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/course-sections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /course-sections/:id : get the "id" courseSection.
     *
     * @param id the id of the courseSectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courseSectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/course-sections/{id}")
    @Timed
    public ResponseEntity<CourseSectionDTO> getCourseSection(@PathVariable Long id) {
        log.debug("REST request to get CourseSection : {}", id);
        CourseSectionDTO courseSectionDTO = courseSectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(courseSectionDTO));
    }

    /**
     * DELETE  /course-sections/:id : delete the "id" courseSection.
     *
     * @param id the id of the courseSectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/course-sections/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourseSection(@PathVariable Long id) {
        log.debug("REST request to delete CourseSection : {}", id);
        courseSectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
