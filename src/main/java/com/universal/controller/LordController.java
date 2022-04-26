package com.universal.controller;

import com.universal.model.dto.lord.CreateLordDto;
import com.universal.model.dto.lord.LordDto;
import com.universal.model.entity.Lord;
import com.universal.model.mapping.LordMapper;
import com.universal.service.lord.LordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/lords")
@RequiredArgsConstructor
public class LordController {
    private final LordService lordService;
    private final LordMapper lordMapper;

    @PostMapping
    public LordDto createLord(@Valid @RequestBody CreateLordDto createLordDto){
        Lord lord = lordMapper.dtoToModel(createLordDto);
        return lordMapper.modelToDto(lordService.add(lord));
    }
}
