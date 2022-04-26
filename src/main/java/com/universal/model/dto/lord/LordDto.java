package com.universal.model.dto.lord;

import com.universal.model.dto.planet.PlanetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LordDto {
    private Long id;
    private String name;
    private Integer age;
    private List<PlanetDto> planets;
}
