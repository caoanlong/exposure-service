package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.AreaStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaStatisticsRepository {
    List<AreaStatistics> findAll();
    AreaStatistics findByCode(@Param("code") String code);
    void update(AreaStatistics areaStatistics);
}
