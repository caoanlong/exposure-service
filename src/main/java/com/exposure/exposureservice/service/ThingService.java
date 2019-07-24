package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.entity.ThingLabel;
import com.exposure.exposureservice.entity.req.ThingDto;

import java.util.Date;
import java.util.List;

public interface ThingService {
    List<Thing> findAll(String title);
    PageBean<List<Thing>> findList(
            String title,
            Integer type,
            Integer sex,
            Date birthDay,
            String area,
            Integer pageIndex,
            Integer pageSize
    );
    Thing findById(Long id);
    void insert(ThingDto thingDto);
    void update(ThingDto thingDto);
    void del(Long id);
}
