package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.Label;
import com.exposure.exposureservice.entity.PageBean;

import java.util.List;

public interface LabelService {
    List<Label> findAll();
    PageBean<List<Label>> findList(String name, Integer pageIndex, Integer pageSize);
    Label findById(Long labelId);
    void insert(Label label);
    void update(Label label);
    void del(Long labelId);
}
