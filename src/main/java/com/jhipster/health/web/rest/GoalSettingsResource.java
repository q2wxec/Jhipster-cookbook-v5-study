package com.jhipster.health.web.rest;
import com.jhipster.health.service.GoalSettingsService;
import com.jhipster.health.web.rest.errors.BadRequestAlertException;
import com.jhipster.health.web.rest.util.HeaderUtil;
import com.jhipster.health.web.rest.util.PaginationUtil;
import com.jhipster.health.service.dto.GoalSettingsDTO;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GoalSettings.
 */
@RestController
@RequestMapping("/api")
public class GoalSettingsResource {

    private final Logger log = LoggerFactory.getLogger(GoalSettingsResource.class);

    private static final String ENTITY_NAME = "goalSettings";

    private final GoalSettingsService goalSettingsService;

    public GoalSettingsResource(GoalSettingsService goalSettingsService) {
        this.goalSettingsService = goalSettingsService;
    }

    /**
     * POST  /goal-settings : Create a new goalSettings.
     *
     * @param goalSettingsDTO the goalSettingsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new goalSettingsDTO, or with status 400 (Bad Request) if the goalSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/goal-settings")
    public ResponseEntity<GoalSettingsDTO> createGoalSettings(@Valid @RequestBody GoalSettingsDTO goalSettingsDTO) throws URISyntaxException {
        log.debug("REST request to save GoalSettings : {}", goalSettingsDTO);
        if (goalSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new goalSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(goalSettingsDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        GoalSettingsDTO result = goalSettingsService.save(goalSettingsDTO);
        return ResponseEntity.created(new URI("/api/goal-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /goal-settings : Updates an existing goalSettings.
     *
     * @param goalSettingsDTO the goalSettingsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated goalSettingsDTO,
     * or with status 400 (Bad Request) if the goalSettingsDTO is not valid,
     * or with status 500 (Internal Server Error) if the goalSettingsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/goal-settings")
    public ResponseEntity<GoalSettingsDTO> updateGoalSettings(@Valid @RequestBody GoalSettingsDTO goalSettingsDTO) throws URISyntaxException {
        log.debug("REST request to update GoalSettings : {}", goalSettingsDTO);
        if (goalSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoalSettingsDTO result = goalSettingsService.save(goalSettingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, goalSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /goal-settings : get all the goalSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of goalSettings in body
     */
    @GetMapping("/goal-settings")
    public ResponseEntity<List<GoalSettingsDTO>> getAllGoalSettings(Pageable pageable) {
        log.debug("REST request to get a page of GoalSettings");
        Page<GoalSettingsDTO> page = goalSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/goal-settings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /goal-settings/:id : get the "id" goalSettings.
     *
     * @param id the id of the goalSettingsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the goalSettingsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/goal-settings/{id}")
    public ResponseEntity<GoalSettingsDTO> getGoalSettings(@PathVariable Long id) {
        log.debug("REST request to get GoalSettings : {}", id);
        Optional<GoalSettingsDTO> goalSettingsDTO = goalSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goalSettingsDTO);
    }

    /**
     * DELETE  /goal-settings/:id : delete the "id" goalSettings.
     *
     * @param id the id of the goalSettingsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/goal-settings/{id}")
    public ResponseEntity<Void> deleteGoalSettings(@PathVariable Long id) {
        log.debug("REST request to delete GoalSettings : {}", id);
        goalSettingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/goal-settings?query=:query : search for the goalSettings corresponding
     * to the query.
     *
     * @param query the query of the goalSettings search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/goal-settings")
    public ResponseEntity<List<GoalSettingsDTO>> searchGoalSettings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GoalSettings for query {}", query);
        Page<GoalSettingsDTO> page = goalSettingsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/goal-settings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
