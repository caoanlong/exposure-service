package com.exposure.exposureservice.entity;

import java.io.Serializable;

public class ThingLabel implements Serializable {
    private Long id;
    private Long thingId;
    private Integer labelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThingId() {
        return thingId;
    }

    public void setThingId(Long thingId) {
        this.thingId = thingId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
}
