package com.exposure.exposureservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 标签
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Label extends Base {
    private Long labelId;
    private String name;
    private String color;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}