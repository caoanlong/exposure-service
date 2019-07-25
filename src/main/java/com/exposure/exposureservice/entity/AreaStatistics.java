package com.exposure.exposureservice.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 地区统计
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaStatistics {
    private String areaName;
    private Long total = 0l;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
