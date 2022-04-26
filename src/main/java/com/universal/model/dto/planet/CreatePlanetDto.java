package com.universal.model.dto.planet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreatePlanetDto {
    @NotNull
    @NotEmpty
    private String name;
}
