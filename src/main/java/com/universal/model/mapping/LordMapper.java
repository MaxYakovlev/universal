package com.universal.model.mapping;

import com.universal.model.dto.lord.CreateLordDto;
import com.universal.model.dto.lord.LordDto;
import com.universal.model.entity.Lord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LordMapper {
    Lord dtoToModel(CreateLordDto createLordDto);
    LordDto modelToDto(Lord lord);
}
