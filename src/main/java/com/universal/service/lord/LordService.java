package com.universal.service.lord;

import com.universal.model.entity.Lord;

import java.util.List;

public interface LordService {
    Lord add(Lord lord);
    List<Lord> findTop10Youngest();
    List<Lord> findWithoutPlanets();
}
