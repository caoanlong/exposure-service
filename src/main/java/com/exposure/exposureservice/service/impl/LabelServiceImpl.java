package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.Label;
import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.repository.LabelRepository;
import com.exposure.exposureservice.service.LabelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    @Override
    public PageBean<List<Label>> findList(String name, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<Label> labels = labelRepository.findList(name, pageStart, pageSize);
        Long total = labelRepository.total(name);
        PageBean<List<Label>> pageBean = new PageBean<>();
        pageBean.setList(labels);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public Label findById(Long labelId) {
        return labelRepository.findById(labelId);
    }

    @Override
    public void insert(Label label) {
        if (StringUtils.isBlank(label.getName()))
            throw new CommonException(ErrorCode.NAMEX_NOT_NULL);
        label.setCreateTime(new Date());
        labelRepository.insert(label);
    }

    @Override
    public void update(Label label) {
        Long labelId = label.getLabelId();
        if (null == labelId)
            throw new CommonException(ErrorCode.ID_NOTNULL);
        label.setUpdateTime(new Date());
        labelRepository.update(label);
    }

    @Override
    public void del(Long labelId) {
        if (null == labelId)
            throw new CommonException(ErrorCode.ID_NOTNULL);
        labelRepository.del(labelId);
    }
}
