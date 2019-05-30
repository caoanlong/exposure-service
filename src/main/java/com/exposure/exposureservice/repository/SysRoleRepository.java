package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository {
    List<SysRole> findAll();
    List<SysRole> findList(
            @Param("roleName") String roleName,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    SysRole findById(Integer id);
    Long total(String roleName);
    void insert(SysRole sysRole);
    void update(SysRole sysRole);
    void del(Integer id);
}
