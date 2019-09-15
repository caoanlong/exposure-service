package com.exposure.exposureservice.service;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.SysUser;

import java.util.List;

public interface SysUserService {
    List<SysUser> findAll();
    PageBean<List<SysUser>> findList(String userName, Integer pageIndex, Integer pageSize);
    SysUser findById(Long id);
    void insert(SysUser sysUser);
    void update(SysUser sysUser);
    void del(Long id);

    SysUser findBaseInfoById(Long id);
    SysUser findBaseInfoByName(String userName);

    SysUser findByNameAndPassword(String userName, String password);

    SysUser findByName(String userName);
}
