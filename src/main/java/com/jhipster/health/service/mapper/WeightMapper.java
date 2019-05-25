package com.jhipster.health.service.mapper;

import com.jhipster.health.domain.*;
import com.jhipster.health.service.dto.WeightDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Weight and its DTO WeightDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface WeightMapper extends EntityMapper<WeightDTO, Weight> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    WeightDTO toDto(Weight weight);

    @Mapping(source = "userId", target = "user")
    Weight toEntity(WeightDTO weightDTO);

    default Weight fromId(Long id) {
        if (id == null) {
            return null;
        }
        Weight weight = new Weight();
        weight.setId(id);
        return weight;
    }
}
