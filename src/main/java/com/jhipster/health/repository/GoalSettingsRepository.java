package com.jhipster.health.repository;

import com.jhipster.health.domain.GoalSettings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GoalSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoalSettingsRepository extends JpaRepository<GoalSettings, Long> {

}
