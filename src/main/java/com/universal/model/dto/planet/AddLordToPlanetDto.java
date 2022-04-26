package com.universal.model.dto.planet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Setter
@Getter
public class AddLordToPlanetDto {
    @NotNull
    @Positive
    private Long id;
}
