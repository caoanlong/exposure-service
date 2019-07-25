package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.AreaStatistics;

import java.util.List;

public interface AreaStatisticsService {
    List<AreaStatistics> findAll();
    AreaStatistics findByName(String areaName);
    void update(AreaStatistics areaStatistics);
}
