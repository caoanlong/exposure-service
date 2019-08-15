package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository {
    List<SysUser> findAll();
    List<SysUser> findList(
            @Param("userName") String userName,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    SysUser findById(Long id);
    Long total(@Param("userName") String userName);
    void insert(SysUser sysUser);
    void update(SysUser sysUser);
    void del(Long id);

    SysUser findBaseInfoById(Long id);

    SysUser findByNameAndPassword(
            @Param("userName") String userName,
            @Param("password") String password
    );

    SysUser findByName(@Param("userName") String userName);

    void insertSysUserRole(List<SysUserRole> sysUserRoles);
    void delSysUserRoleByUserId(Long userId);
}
