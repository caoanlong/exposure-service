package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.AreaStatistics;
import com.exposure.exposureservice.repository.AreaStatisticsRepository;
import com.exposure.exposureservice.service.AreaStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AreaStatisticsServiceImpl implements AreaStatisticsService {

    @Autowired
    private AreaStatisticsRepository areaStatisticsRepository;

    @Override
    public List<AreaStatistics> findAll() {
        return areaStatisticsRepository.findAll();
    }

    @Override
    public AreaStatistics findByName(String areaName) {
        return areaStatisticsRepository.findByName(areaName);
    }

    @Override
    public void update(AreaStatistics areaStatistics) {
        areaStatisticsRepository.update(areaStatistics);
    }
}
