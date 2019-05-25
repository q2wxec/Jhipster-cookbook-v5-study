package com.jhipster.health.service.mapper;

import com.jhipster.health.domain.*;
import com.jhipster.health.service.dto.GoalSettingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GoalSettings and its DTO GoalSettingsDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GoalSettingsMapper extends EntityMapper<GoalSettingsDTO, GoalSettings> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    GoalSettingsDTO toDto(GoalSettings goalSettings);

    @Mapping(source = "userId", target = "user")
    GoalSettings toEntity(GoalSettingsDTO goalSettingsDTO);

    default GoalSettings fromId(Long id) {
        if (id == null) {
            return null;
        }
        GoalSettings goalSettings = new GoalSettings();
        goalSettings.setId(id);
        return goalSettings;
    }
}
