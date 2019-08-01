package com.exposure.exposureservice.service.impl;

import com.exposure.exposureservice.entity.PageBean;
import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.repository.SysUserRepository;
import com.exposure.exposureservice.service.SysUserService;
import com.exposure.exposureservice.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Override
    public PageBean<List<SysUser>> findList(String userName, Integer pageIndex, Integer pageSize) {
        Integer pageStart = (pageIndex - 1) * pageSize;
        List<SysUser> sysUsers = sysUserRepository.findList(userName, pageStart, pageSize);
        Long total = sysUserRepository.total(userName);
        PageBean<List<SysUser>> pageBean = new PageBean<>();
        pageBean.setList(sysUsers);
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserRepository.findById(id);
    }

    @Override
    public void insert(SysUser sysUser) {
        Long id = snowFlake.nextId();
        sysUser.setId(id);
        sysUser.setCreateTime(new Date());
        sysUserRepository.insert(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUser.setUpdateTime(new Date());
        sysUserRepository.update(sysUser);
    }

    @Override
    public void del(Long id) {
        sysUserRepository.del(id);
    }

    @Override
    public SysUser findByNameAndPassword(String userName, String password) {
        return sysUserRepository.findByNameAndPassword(userName, password);
    }

    @Override
    public SysUser findByName(String userName) {
        return sysUserRepository.findByName(userName);
    }
}
