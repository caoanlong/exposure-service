package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.Thing;
import com.exposure.exposureservice.entity.ThingLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThingRepostory {
    List<Thing> findAll();
    List<Thing> findList(
            @Param("title") String title,
            @Param("type") Integer type,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    Thing findById(Long id);
    void updateViews(Long id);
    Long total(@Param("title") String title, @Param("type") Integer type);
    void insert(Thing thing);
    void update(Thing thing);
    void del(Long id);
    void insertThingLabels(List<ThingLabel> thingLabels);
    void delThingLabelByThingIds(List<Long> thingIds);
}
