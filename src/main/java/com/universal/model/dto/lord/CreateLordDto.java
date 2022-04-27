package com.universal.model.dto.lord;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLordDto {
    @NotNull
    @NotEmpty
    private String name;

    @Positive
    @NotNull
    private Integer age;
}
