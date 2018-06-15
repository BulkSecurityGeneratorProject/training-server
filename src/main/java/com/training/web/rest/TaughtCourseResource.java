package com.training.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.training.service.TaughtCourseService;
import com.training.web.rest.errors.BadRequestAlertException;
import com.training.web.rest.util.HeaderUtil;
import com.training.web.rest.util.PaginationUtil;
import com.training.service.dto.TaughtCourseDTO;
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
 * REST controller for managing TaughtCourse.
 */
@RestController
@RequestMapping("/api")
public class TaughtCourseResource {

    private final Logger log = LoggerFactory.getLogger(TaughtCourseResource.class);

    private static final String ENTITY_NAME = "taughtCourse";

    private final TaughtCourseService taughtCourseService;

    public TaughtCourseResource(TaughtCourseService taughtCourseService) {
        this.taughtCourseService = taughtCourseService;
    }

    /**
     * POST  /taught-courses : Create a new taughtCourse.
     *
     * @param taughtCourseDTO the taughtCourseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taughtCourseDTO, or with status 400 (Bad Request) if the taughtCourse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/taught-courses")
    @Timed
    public ResponseEntity<TaughtCourseDTO> createTaughtCourse(@RequestBody TaughtCourseDTO taughtCourseDTO) throws URISyntaxException {
        log.debug("REST request to save TaughtCourse : {}", taughtCourseDTO);
        if (taughtCourseDTO.getId() != null) {
            throw new BadRequestAlertException("A new taughtCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaughtCourseDTO result = taughtCourseService.save(taughtCourseDTO);
        return ResponseEntity.created(new URI("/api/taught-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taught-courses : Updates an existing taughtCourse.
     *
     * @param taughtCourseDTO the taughtCourseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taughtCourseDTO,
     * or with status 400 (Bad Request) if the taughtCourseDTO is not valid,
     * or with status 500 (Internal Server Error) if the taughtCourseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/taught-courses")
    @Timed
    public ResponseEntity<TaughtCourseDTO> updateTaughtCourse(@RequestBody TaughtCourseDTO taughtCourseDTO) throws URISyntaxException {
        log.debug("REST request to update TaughtCourse : {}", taughtCourseDTO);
        if (taughtCourseDTO.getId() == null) {
            return createTaughtCourse(taughtCourseDTO);
        }
        TaughtCourseDTO result = taughtCourseService.save(taughtCourseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taughtCourseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taught-courses : get all the taughtCourses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taughtCourses in body
     */
    @GetMapping("/taught-courses")
    @Timed
    public ResponseEntity<List<TaughtCourseDTO>> getAllTaughtCourses(Pageable pageable) {
        log.debug("REST request to get a page of TaughtCourses");
        Page<TaughtCourseDTO> page = taughtCourseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taught-courses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taught-courses/:id : get the "id" taughtCourse.
     *
     * @param id the id of the taughtCourseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taughtCourseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/taught-courses/{id}")
    @Timed
    public ResponseEntity<TaughtCourseDTO> getTaughtCourse(@PathVariable Long id) {
        log.debug("REST request to get TaughtCourse : {}", id);
        TaughtCourseDTO taughtCourseDTO = taughtCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taughtCourseDTO));
    }

    /**
     * DELETE  /taught-courses/:id : delete the "id" taughtCourse.
     *
     * @param id the id of the taughtCourseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/taught-courses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaughtCourse(@PathVariable Long id) {
        log.debug("REST request to delete TaughtCourse : {}", id);
        taughtCourseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
