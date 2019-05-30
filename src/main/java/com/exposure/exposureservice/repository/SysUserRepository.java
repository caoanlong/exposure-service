package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.SysUser;
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
    SysUser findById(Integer id);
    Long total(String userName);
    void insert(SysUser sysUser);
    void update(SysUser sysUser);
    void del(Integer id);

    SysUser findByNameAndPassword(
            @Param("userName") String userName,
            @Param("password") String password
    );
}
