package com.jhipster.health.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of GoalSettingsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GoalSettingsSearchRepositoryMockConfiguration {

    @MockBean
    private GoalSettingsSearchRepository mockGoalSettingsSearchRepository;

}
