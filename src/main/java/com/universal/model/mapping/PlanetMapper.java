package com.universal.model.mapping;

import com.universal.model.dto.planet.PlanetDto;
import com.universal.model.entity.Planet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanetMapper {
    List<PlanetDto> modelToDto(List<Planet> planets);
}
