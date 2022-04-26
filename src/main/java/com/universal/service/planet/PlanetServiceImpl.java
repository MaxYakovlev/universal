package com.universal.service.planet;

import com.universal.exception.AccessRuntimeException;
import com.universal.model.entity.Planet;
import com.universal.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService{
    private final PlanetRepository planetRepository;

    @Override
    public Planet add(Planet planet) {
        return planetRepository.save(planet);
    }

    @Override
    public void delete(Long id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new AccessRuntimeException("Планета не найдена.", HttpStatus.NOT_FOUND));

        planetRepository.delete(planet);
    }
}
