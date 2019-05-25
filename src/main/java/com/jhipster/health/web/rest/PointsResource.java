package com.jhipster.health.web.rest;
import com.jhipster.health.repository.UserRepository;
import com.jhipster.health.security.AuthoritiesConstants;
import com.jhipster.health.security.SecurityUtils;
import com.jhipster.health.service.PointsService;
import com.jhipster.health.service.UserService;
import com.jhipster.health.web.rest.errors.BadRequestAlertException;
import com.jhipster.health.web.rest.util.HeaderUtil;
import com.jhipster.health.web.rest.util.PaginationUtil;
import com.jhipster.health.service.dto.PointsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Points.
 */
@RestController
@RequestMapping("/api")
public class PointsResource {

    private final Logger log = LoggerFactory.getLogger(PointsResource.class);

    private static final String ENTITY_NAME = "points";

    private final PointsService pointsService;
    private final UserService userService;


    public PointsResource(PointsService pointsService,UserService userService) {
        this.pointsService = pointsService;
        this.userService = userService;
    }

    /**
     * POST  /points : Create a new points.
     *
     * @param pointsDTO the pointsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pointsDTO, or with status 400 (Bad Request) if the points has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/points")
    public ResponseEntity<PointsDTO> createPoints(@Valid @RequestBody PointsDTO pointsDTO) throws URISyntaxException {
        log.debug("REST request to save Points : {}", pointsDTO);
        if (pointsDTO.getId() != null) {
            throw new BadRequestAlertException("A new points cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            log.debug("No user passed in, using current user: {}", SecurityUtils.getCurrentUserLogin());
            pointsDTO.setUserId(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId());
        }


        PointsDTO result = pointsService.save(pointsDTO);
        return ResponseEntity.created(new URI("/api/points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /points : Updates an existing points.
     *
     * @param pointsDTO the pointsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pointsDTO,
     * or with status 400 (Bad Request) if the pointsDTO is not valid,
     * or with status 500 (Internal Server Error) if the pointsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/points")
    public ResponseEntity<PointsDTO> updatePoints(@Valid @RequestBody PointsDTO pointsDTO) throws URISyntaxException {
        log.debug("REST request to update Points : {}", pointsDTO);
        if (pointsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PointsDTO result = pointsService.save(pointsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pointsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /points : get all the points.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of points in body
     */
    @GetMapping("/points")
    public ResponseEntity<List<PointsDTO>> getAllPoints(Pageable pageable) {
        log.debug("REST request to get a page of Points");
        Page<PointsDTO> page;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            page = pointsService.findAllByOrderByDateDesc(pageable);
        }else{
            page = pointsService.findByUserIsCurrentUser(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/points");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /points/:id : get the "id" points.
     *
     * @param id the id of the pointsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pointsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/points/{id}")
    public ResponseEntity<PointsDTO> getPoints(@PathVariable Long id) {
        log.debug("REST request to get Points : {}", id);
        Optional<PointsDTO> pointsDTO = pointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointsDTO);
    }

    /**
     * DELETE  /points/:id : delete the "id" points.
     *
     * @param id the id of the pointsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/points/{id}")
    public ResponseEntity<Void> deletePoints(@PathVariable Long id) {
        log.debug("REST request to delete Points : {}", id);
        pointsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/points?query=:query : search for the points corresponding
     * to the query.
     *
     * @param query the query of the points search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/points")
    public ResponseEntity<List<PointsDTO>> searchPoints(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Points for query {}", query);
        Page<PointsDTO> page = pointsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/points");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
