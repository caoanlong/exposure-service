package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.SysRole;

import java.util.List;

public interface SysRoleService {
    List<SysRole> findAll();
    PageBean<List<SysRole>> findList(String roleName, Integer pageIndex, Integer pageSize);
    SysRole findById(Integer id);
    void insert(SysRole sysRole);
    void update(SysRole sysRole);
    void del(Integer id);

    List<String> findRolesByUserId(Long userId);
}
