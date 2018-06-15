package com.training.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.training.service.CareerPathService;
import com.training.web.rest.errors.BadRequestAlertException;
import com.training.web.rest.util.HeaderUtil;
import com.training.web.rest.util.PaginationUtil;
import com.training.service.dto.CareerPathDTO;
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
 * REST controller for managing CareerPath.
 */
@RestController
@RequestMapping("/api")
public class CareerPathResource {

    private final Logger log = LoggerFactory.getLogger(CareerPathResource.class);

    private static final String ENTITY_NAME = "careerPath";

    private final CareerPathService careerPathService;

    public CareerPathResource(CareerPathService careerPathService) {
        this.careerPathService = careerPathService;
    }

    /**
     * POST  /career-paths : Create a new careerPath.
     *
     * @param careerPathDTO the careerPathDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPathDTO, or with status 400 (Bad Request) if the careerPath has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-paths")
    @Timed
    public ResponseEntity<CareerPathDTO> createCareerPath(@RequestBody CareerPathDTO careerPathDTO) throws URISyntaxException {
        log.debug("REST request to save CareerPath : {}", careerPathDTO);
        if (careerPathDTO.getId() != null) {
            throw new BadRequestAlertException("A new careerPath cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CareerPathDTO result = careerPathService.save(careerPathDTO);
        return ResponseEntity.created(new URI("/api/career-paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-paths : Updates an existing careerPath.
     *
     * @param careerPathDTO the careerPathDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPathDTO,
     * or with status 400 (Bad Request) if the careerPathDTO is not valid,
     * or with status 500 (Internal Server Error) if the careerPathDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-paths")
    @Timed
    public ResponseEntity<CareerPathDTO> updateCareerPath(@RequestBody CareerPathDTO careerPathDTO) throws URISyntaxException {
        log.debug("REST request to update CareerPath : {}", careerPathDTO);
        if (careerPathDTO.getId() == null) {
            return createCareerPath(careerPathDTO);
        }
        CareerPathDTO result = careerPathService.save(careerPathDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, careerPathDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-paths : get all the careerPaths.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of careerPaths in body
     */
    @GetMapping("/career-paths")
    @Timed
    public ResponseEntity<List<CareerPathDTO>> getAllCareerPaths(Pageable pageable) {
        log.debug("REST request to get a page of CareerPaths");
        Page<CareerPathDTO> page = careerPathService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/career-paths");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /career-paths/:id : get the "id" careerPath.
     *
     * @param id the id of the careerPathDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPathDTO, or with status 404 (Not Found)
     */
    @GetMapping("/career-paths/{id}")
    @Timed
    public ResponseEntity<CareerPathDTO> getCareerPath(@PathVariable Long id) {
        log.debug("REST request to get CareerPath : {}", id);
        CareerPathDTO careerPathDTO = careerPathService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(careerPathDTO));
    }

    /**
     * DELETE  /career-paths/:id : delete the "id" careerPath.
     *
     * @param id the id of the careerPathDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-paths/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPath(@PathVariable Long id) {
        log.debug("REST request to delete CareerPath : {}", id);
        careerPathService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
