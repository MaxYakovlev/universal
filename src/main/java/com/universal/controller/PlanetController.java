package com.universal.controller;

import com.universal.model.dto.planet.CreatePlanetDto;
import com.universal.model.dto.planet.PlanetDto;
import com.universal.model.entity.Planet;
import com.universal.model.mapping.PlanetMapper;
import com.universal.service.planet.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetService planetService;
    private final PlanetMapper planetMapper;

    @PostMapping
    public PlanetDto createPlanet(@Valid @RequestBody CreatePlanetDto createPlanetDto){
        Planet planet = planetMapper.dtoToModel(createPlanetDto);
        return planetMapper.modelToDto(planetService.add(planet));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePlanet(@PathVariable Long id){
        planetService.delete(id);
        return HttpStatus.ACCEPTED;
    }
}
