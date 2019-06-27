package com.exposure.exposureservice.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 标签
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Label implements Serializable {
    private Long id;
    private String name;
    private String color;
    
}