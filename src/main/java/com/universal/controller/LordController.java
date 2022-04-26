package com.universal.controller;

import com.universal.model.dto.lord.CreateLordDto;
import com.universal.model.dto.lord.LordDto;
import com.universal.model.entity.Lord;
import com.universal.model.mapping.LordMapper;
import com.universal.service.lord.LordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/top-10-youngest")
    public List<LordDto> findTop10Youngest(){
        return lordMapper.modelToDto(lordService.findTop10Youngest());
    }

    @GetMapping("/without-planets")
    public List<LordDto> findWithoutPlanets(){
        return lordMapper.modelToDto(lordService.findWithoutPlanets());
    }
}
