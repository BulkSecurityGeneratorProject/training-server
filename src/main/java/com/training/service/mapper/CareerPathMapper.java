package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.CareerPathDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CareerPath and its DTO CareerPathDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CareerPathMapper extends EntityMapper<CareerPathDTO, CareerPath> {



    default CareerPath fromId(Long id) {
        if (id == null) {
            return null;
        }
        CareerPath careerPath = new CareerPath();
        careerPath.setId(id);
        return careerPath;
    }
}
