package com.jhipster.health.service.impl;

import com.jhipster.health.service.GoalSettingsService;
import com.jhipster.health.domain.GoalSettings;
import com.jhipster.health.repository.GoalSettingsRepository;
import com.jhipster.health.repository.UserRepository;
import com.jhipster.health.repository.search.GoalSettingsSearchRepository;
import com.jhipster.health.service.dto.GoalSettingsDTO;
import com.jhipster.health.service.mapper.GoalSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GoalSettings.
 */
@Service
@Transactional
public class GoalSettingsServiceImpl implements GoalSettingsService {

    private final Logger log = LoggerFactory.getLogger(GoalSettingsServiceImpl.class);

    private final GoalSettingsRepository goalSettingsRepository;

    private final GoalSettingsMapper goalSettingsMapper;

    private final GoalSettingsSearchRepository goalSettingsSearchRepository;

    private final UserRepository userRepository;

    public GoalSettingsServiceImpl(GoalSettingsRepository goalSettingsRepository, GoalSettingsMapper goalSettingsMapper, GoalSettingsSearchRepository goalSettingsSearchRepository, UserRepository userRepository) {
        this.goalSettingsRepository = goalSettingsRepository;
        this.goalSettingsMapper = goalSettingsMapper;
        this.goalSettingsSearchRepository = goalSettingsSearchRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a goalSettings.
     *
     * @param goalSettingsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GoalSettingsDTO save(GoalSettingsDTO goalSettingsDTO) {
        log.debug("Request to save GoalSettings : {}", goalSettingsDTO);
        GoalSettings goalSettings = goalSettingsMapper.toEntity(goalSettingsDTO);
        long userId = goalSettingsDTO.getUserId();
        userRepository.findById(userId).ifPresent(goalSettings::user);
        goalSettings = goalSettingsRepository.save(goalSettings);
        GoalSettingsDTO result = goalSettingsMapper.toDto(goalSettings);
        goalSettingsSearchRepository.save(goalSettings);
        return result;
    }

    /**
     * Get all the goalSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoalSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoalSettings");
        return goalSettingsRepository.findAll(pageable)
            .map(goalSettingsMapper::toDto);
    }


    /**
     * Get one goalSettings by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GoalSettingsDTO> findOne(Long id) {
        log.debug("Request to get GoalSettings : {}", id);
        return goalSettingsRepository.findById(id)
            .map(goalSettingsMapper::toDto);
    }

    /**
     * Delete the goalSettings by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoalSettings : {}", id);
        goalSettingsRepository.deleteById(id);
        goalSettingsSearchRepository.deleteById(id);
    }

    /**
     * Search for the goalSettings corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GoalSettingsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GoalSettings for query {}", query);
        return goalSettingsSearchRepository.search(queryStringQuery(query), pageable)
            .map(goalSettingsMapper::toDto);
    }
}
