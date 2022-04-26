package com.universal.model.mapping;

import com.universal.model.dto.lord.CreateLordDto;
import com.universal.model.dto.lord.LordDto;
import com.universal.model.entity.Lord;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LordMapper {
    Lord dtoToModel(CreateLordDto createLordDto);
    LordDto modelToDto(Lord lord);
    List<LordDto> modelToDto(List<Lord> lords);
}
