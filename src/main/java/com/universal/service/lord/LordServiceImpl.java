package com.universal.service.lord;

import com.universal.model.entity.Lord;
import com.universal.repository.LordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LordServiceImpl implements LordService{
    private final LordRepository lordRepository;

    @Override
    public Lord add(Lord lord) {
        return lordRepository.save(lord);
    }
}
