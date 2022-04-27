package com.universal.controller;

import com.universal.common.PostgresTestContainer;
import com.universal.exception.AccessRuntimeException;
import com.universal.model.dto.planet.AddLordToPlanetDto;
import com.universal.model.dto.planet.CreatePlanetDto;
import com.universal.model.entity.Lord;
import com.universal.model.entity.Planet;
import com.universal.model.mapping.PlanetMapper;
import com.universal.repository.LordRepository;
import com.universal.repository.PlanetRepository;
import com.universal.service.planet.PlanetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlanetControllerTest extends PostgresTestContainer{
    @Autowired private PlanetController planetController;
    @Autowired private PlanetService planetService;
    @Autowired private PlanetRepository planetRepository;
    @Autowired private LordRepository lordRepository;
    @Autowired private PlanetMapper planetMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void clear(){
        planetRepository.deleteAll();
        lordRepository.deleteAll();
    }

    @Test
    public void DeletePlanet_IdNotExists_AccessRuntimeException() {
        assertThrows(AccessRuntimeException.class, () -> {
            planetController.deletePlanet(anyLong());
        });
    }

    @Test
    public void AddLordToPlanet_PlanetIdNotExists_AccessRuntimeException() {
        assertThrows(AccessRuntimeException.class, () -> {
            planetController.addLordToPlanet(anyLong(), new AddLordToPlanetDto(anyLong()));
        });
    }

    @Test
    public void AddLordToPlanet_LordDtoInvalid_StatusCode400() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(planetController).build();

        List<AddLordToPlanetDto> lords = Arrays.asList(
            new AddLordToPlanetDto(null),
            new AddLordToPlanetDto(-1L)
        );

        for(AddLordToPlanetDto lord: lords){
            String json = objectMapper.writeValueAsString(lord);

            mockMvc.perform(
                            put("/planets/1/lord")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json))
                    .andExpect(status().is(400));
        }
    }

    @Test
    @Transactional
    public void AddLordToPlanet_Successfully(){
        Planet planet = new Planet(null, anyString(), null);
        Lord lord = new Lord(null, anyString(), anyInt(), null);

        planetRepository.save(planet);
        lordRepository.save(lord);

        planetController.addLordToPlanet(planet.getId(), new AddLordToPlanetDto(lord.getId()));

        assertNotNull(planet.getLord());
    }

    @Test
    @Transactional
    public void AddLordToPlanet_LordDtoValid_StatusCodeOk() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(planetController).build();

        Planet planet = new Planet(null, anyString(), null);
        Lord lord = new Lord(null, anyString(), anyInt(), null);

        planetRepository.save(planet);
        lordRepository.save(lord);

        AddLordToPlanetDto lordDto = new AddLordToPlanetDto(lord.getId());

        String json = objectMapper.writeValueAsString(lordDto);

        mockMvc.perform(
                        put("/planets/" + planet.getId() + "/lord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void AddLordToPlanet_LordIdNotExists_AccessRuntimeException() {
        Planet planet = new Planet(null, anyString(), null);

        planetRepository.save(planet);

        assertThrows(AccessRuntimeException.class, () -> {
            planetController.addLordToPlanet(planet.getId(), new AddLordToPlanetDto(anyLong()));
        });
    }

    @Test
    public void DeletePlanet_Successfully(){
        Planet planet = new Planet(null, anyString(), null);

        planetRepository.save(planet);

        planetController.deletePlanet(planet.getId());

        assertEquals(0, planetRepository.findAll().size());
    }

    @Test
    public void CreatePlanet_Successfully(){
        CreatePlanetDto planetDto = new CreatePlanetDto(anyString());

        assertEquals(0, planetRepository.findAll().size());

        planetController.createPlanet(planetDto);

        assertEquals(1, planetRepository.findAll().size());
    }

    @Test
    public void CreatePlanet_PlanetDtoInvalid_StatusCode400() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(planetController).build();

        List<CreatePlanetDto> planets = Arrays.asList(
                new CreatePlanetDto(null),
                new CreatePlanetDto("")
        );

        for(CreatePlanetDto planet: planets){
            String json = objectMapper.writeValueAsString(planet);

            mockMvc.perform(
                            post("/planets")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json))
                    .andExpect(status().is(400));
        }
    }

    @Test
    public void CreatePlanet_PlanetDtoValid_StatusCodeOk() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(planetController).build();

        CreatePlanetDto planet = new CreatePlanetDto("planet1");

        String json = objectMapper.writeValueAsString(planet);

        mockMvc.perform(
                        post("/planets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk());
    }
}
