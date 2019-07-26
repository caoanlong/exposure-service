package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.AreaStatistics;

import java.util.List;

public interface AreaStatisticsService {
    List<AreaStatistics> findAll();
    AreaStatistics findByCode(String code);
    void update(AreaStatistics areaStatistics);
}
