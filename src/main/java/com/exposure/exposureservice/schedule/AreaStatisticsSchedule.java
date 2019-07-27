package com.exposure.exposureservice.schedule;

import com.exposure.exposureservice.entity.AreaStatistics;
import com.exposure.exposureservice.service.AreaStatisticsService;
import com.exposure.exposureservice.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaStatisticsSchedule {

    @Autowired
    private AreaStatisticsService areaStatisticsService;

    @Autowired
    private ThingService thingService;


    /**
     * 定时更新地区总数据
     * 初次间隔5秒
     * 10分钟执行一次
     */
    @Scheduled(initialDelay = 5000, fixedDelay=600000)
    public void update() {
        System.out.println("start!!!!");
        List<AreaStatistics> all = areaStatisticsService.findAll();
        for (AreaStatistics areaStatistics: all) {
            String code = areaStatistics.getCode();
            Long total = thingService.total(null, null, null, null, code);
            System.out.println(code + ":" + total);
            areaStatistics.setTotal(total);
            areaStatisticsService.update(areaStatistics);
        }
    }
}
