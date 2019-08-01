package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.SysRole;
import com.exposure.exposureservice.repository.SysRoleRepository;
import com.exposure.exposureservice.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public List<SysRole> findAll() {
        return sysRoleRepository.findAll();
    }

    @Override
    public PageBean<List<SysRole>> findList(String roleName, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<SysRole> sysRoles = sysRoleRepository.findList(roleName, pageStart, pageSize);
        Long total = sysRoleRepository.total(roleName);
        PageBean<List<SysRole>> pageBean = new PageBean<>();
        pageBean.setList(sysRoles);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public SysRole findById(Integer id) {
        return sysRoleRepository.findById(id);
    }

    @Override
    public void insert(SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        sysRoleRepository.insert(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        sysRoleRepository.update(sysRole);
    }

    @Override
    public void del(Integer id) {
        sysRoleRepository.del(id);
    }
}
