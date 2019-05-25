package com.jhipster.health.repository.search;

import com.jhipster.health.domain.GoalSettings;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GoalSettings entity.
 */
public interface GoalSettingsSearchRepository extends ElasticsearchRepository<GoalSettings, Long> {
}
