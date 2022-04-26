package com.universal.service.lord;

import com.universal.exception.AccessRuntimeException;
import com.universal.model.entity.Lord;
import com.universal.repository.LordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LordServiceImpl implements LordService{
    private final LordRepository lordRepository;

    @Override
    public Lord add(Lord lord) {
        return lordRepository.save(lord);
    }

    @Override
    public List<Lord> findTop10Youngest() {
        return lordRepository.findTop10Youngest()
                .orElseThrow(() -> new AccessRuntimeException("Повелители не найдены", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Lord> findWithoutPlanets() {
        return lordRepository.findWithoutPlanets()
                .orElseThrow(() -> new AccessRuntimeException("Повелители не найдены", HttpStatus.NOT_FOUND));
    }
}
