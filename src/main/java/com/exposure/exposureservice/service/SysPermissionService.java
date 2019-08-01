package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.SysPermission;

import java.util.List;

public interface SysPermissionService {
    List<SysPermission> findAll();
    PageBean<List<SysPermission>> findList(
            String perName,
            String perType,
            Integer pageIndex,
            Integer pageSize
    );
    SysPermission findById(Integer id);
    Long total(String perName, String perType);
    void insert(SysPermission sysPermission);
    void update(SysPermission sysPermission);
    void del(Integer id);

    List<String> findPermissionByUserId(Long userId);
}
