package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThingService {
    List<Thing> findAll();
    PageBean<List<Thing>> findList(String title, Integer type, Integer pageIndex, Integer pageSize);
    Thing findById(Integer id);
    void insert(Thing thing);
    void update(Thing thing);
    void del(Integer id);
}
