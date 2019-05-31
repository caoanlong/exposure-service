package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.repository.ThingRepostory;
import com.exposure.exposureservice.service.ThingService;
import com.exposure.exposureservice.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ThingRepostory thingRepostory;

    @Override
    public List<Thing> findAll() {
        return thingRepostory.findAll();
    }

    @Override
    public PageBean<List<Thing>> findList(String title, Integer type, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<Thing> things = thingRepostory.findList(title, type, pageStart, pageSize);
        Long total = thingRepostory.total(title, type);
        PageBean<List<Thing>> pageBean = new PageBean<>();
        pageBean.setList(things);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public Thing findById(Long id) {
        return thingRepostory.findById(id);
    }

    @Override
    public void insert(Thing thing) {
        Long id = snowFlake.nextId();
        thing.setCreateId(id);
        thing.setCreateTime(new Date());
        thingRepostory.insert(thing);
    }

    @Override
    public void update(Thing thing) {
        thingRepostory.update(thing);
    }

    @Override
    public void del(Long id) {
        thingRepostory.del(id);
    }
}
