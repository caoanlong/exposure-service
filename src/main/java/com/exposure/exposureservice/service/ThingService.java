package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;

import java.util.List;

public interface ThingService {
    List<Thing> findAll();
    PageBean<List<Thing>> findList(String title, Integer type, Integer pageIndex, Integer pageSize);
    Thing findById(Long id);
    void insert(Thing thing);
    void update(Thing thing);
    void del(Long id);
}
