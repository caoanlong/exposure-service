package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.Label;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository {
    List<Label> findAll();
    List<Label> findList(
            @Param("name") String name,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    Label findById(Long labelId);
    Long total(@Param("name") String name);
    void insert(Label label);
    void update(Label label);
    void del(Long labelId);
}
