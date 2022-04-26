package com.universal.service.planet;

import com.universal.model.entity.Planet;

public interface PlanetService {
    Planet add(Planet planet);
    void delete(Long id);
    void addLordToPlanet(Long planetId, Long lordId);
}
