package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.SysPermission;
import com.exposure.exposureservice.repository.SysPermissionRepository;
import com.exposure.exposureservice.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Override
    public List<SysPermission> findAll() {
        return sysPermissionRepository.findAll();
    }

    @Override
    public PageBean<List<SysPermission>> findList(String perName, String perType, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<SysPermission> sysPermissions = sysPermissionRepository.findList(perName, perType, pageStart, pageSize);
        Long total = sysPermissionRepository.total(perName, perType);
        PageBean<List<SysPermission>> pageBean = new PageBean<>();
        pageBean.setList(sysPermissions);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public SysPermission findById(Integer id) {
        return sysPermissionRepository.findById(id);
    }

    @Override
    public Long total(String perName, String perType) {
        return sysPermissionRepository.total(perName, perType);
    }

    @Override
    public void insert(SysPermission sysPermission) {

    }

    @Override
    public void update(SysPermission sysPermission) {

    }

    @Override
    public void del(Integer id) {

    }

    @Override
    public List<String> findPermissionByUserId(Long userId) {
        return null;
    }
}
