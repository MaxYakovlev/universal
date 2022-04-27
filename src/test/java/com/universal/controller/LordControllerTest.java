package com.universal.controller;

import com.universal.model.dto.lord.CreateLordDto;
import com.universal.model.dto.lord.LordDto;
import com.universal.model.entity.Lord;
import com.universal.model.entity.Planet;
import com.universal.model.mapping.LordMapper;
import com.universal.repository.LordRepository;
import com.universal.repository.PlanetRepository;
import com.universal.service.lord.LordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
public class LordControllerTest {
    @Autowired private LordController lordController;
    @Autowired private LordRepository lordRepository;
    @Autowired private PlanetRepository planetRepository;
    @Autowired private LordService lordService;
    @Autowired private LordMapper lordMapper;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("postgres")
            .withPassword("root")
            .withDatabaseName("universal_db");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
    public void clear(){
        planetRepository.deleteAll();
        lordRepository.deleteAll();
    }

    @Test
    public void CreateLord_LordDtoValid_CreatedSuccessfully(){
        CreateLordDto createLordDto = new CreateLordDto(anyString(), anyInt());

        assertEquals(0, lordRepository.findAll().size());

        lordController.createLord(createLordDto);

        assertEquals(1, lordRepository.findAll().size());
    }

    @Test
    @Transactional
    public void FindLordsWithoutPlanets_Successfully(){
        initLordsAndPlanets();

        List<LordDto> lordsWithoutPlanets = lordController.findWithoutPlanets();

        lordsWithoutPlanets.forEach(lord -> assertNull(lord.getPlanets()));
    }

    @Test
    @Transactional
    public void FindTop10Youngest_Successfully(){
        initLordsAndPlanets();

        boolean sorted = false;
        List<LordDto> top10YoungestLords = lordController.findTop10Youngest();

        for(int i = 0; i < top10YoungestLords.size() - 1; i++){
            sorted = top10YoungestLords.get(i).getAge() <= top10YoungestLords.get(i + 1).getAge();
        }

        assertTrue(top10YoungestLords.size() <= 10);
        assertTrue(sorted);
    }

    @Test
    public void CreateLord_LordDtoValid_StatusCodeOk() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(lordController).build();

        CreateLordDto createLordDto = new CreateLordDto("testname", 11);

        String json = objectMapper.writeValueAsString(createLordDto);

        mockMvc.perform(
                        post("/lords")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void CreateLord_LordDtoInvalid_StatusCode400() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(lordController).build();

        List<CreateLordDto> lords = Arrays.asList(
            new CreateLordDto(null, null),
            new CreateLordDto("", null),
            new CreateLordDto("testname", null),
            new CreateLordDto("testname", -1)
        );

        for(CreateLordDto lord: lords){
            String json = objectMapper.writeValueAsString(lord);

            mockMvc.perform(
                            post("/lords")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json))
                    .andExpect(status().is(400));
        }
    }

    private void initLordsAndPlanets(){
        Random rand = new Random();
        List<Lord> lords = new ArrayList<>();

        for(int i = 0; i < 15; i++){
            int age = rand.nextInt(100);
            lords.add(new Lord(null, anyString(), age, null));
        }

        lordRepository.saveAll(lords);

        Planet planet = new Planet(null, anyString(), lords.get(0));

        planetRepository.save(planet);
    }
}
