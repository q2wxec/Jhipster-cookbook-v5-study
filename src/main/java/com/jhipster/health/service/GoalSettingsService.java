package com.jhipster.health.service;

import com.jhipster.health.service.dto.GoalSettingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing GoalSettings.
 */
public interface GoalSettingsService {

    /**
     * Save a goalSettings.
     *
     * @param goalSettingsDTO the entity to save
     * @return the persisted entity
     */
    GoalSettingsDTO save(GoalSettingsDTO goalSettingsDTO);

    /**
     * Get all the goalSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GoalSettingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" goalSettings.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GoalSettingsDTO> findOne(Long id);

    /**
     * Delete the "id" goalSettings.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the goalSettings corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GoalSettingsDTO> search(String query, Pageable pageable);
}
