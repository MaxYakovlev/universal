package com.universal.service.planet;

import com.universal.exception.AccessRuntimeException;
import com.universal.model.entity.Lord;
import com.universal.model.entity.Planet;
import com.universal.repository.LordRepository;
import com.universal.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService{
    private final PlanetRepository planetRepository;
    private final LordRepository lordRepository;

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

    @Override
    public void addLordToPlanet(Long planetId, Long lordId) {
        Lord lord = lordRepository.findById(lordId)
                .orElseThrow(() -> new AccessRuntimeException("Повелитель не найден.", HttpStatus.NOT_FOUND));

        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(() -> new AccessRuntimeException("Планета не найдена.", HttpStatus.NOT_FOUND));

        planet.setLord(lord);
        planetRepository.save(planet);
    }
}
