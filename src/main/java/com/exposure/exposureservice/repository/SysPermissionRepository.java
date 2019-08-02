package com.exposure.exposureservice.repository;

import com.exposure.exposureservice.entity.SysPermission;
import com.exposure.exposureservice.entity.SysRole;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionRepository {
    List<SysPermission> findAll();
    List<SysPermission> findList(
            @Param("perName") String perName,
            @Param("perType") String perType,
            @Param("pageStart") Integer pageStart,
            @Param("pageSize") Integer pageSize
    );
    SysPermission findById(Integer id);
    Long total(String perName, String perType);
    void insert(SysPermission sysPermission);
    void update(SysPermission sysPermission);
    void del(Integer id);

    List<String> findPermissionByUserId(@Param("userId") Long userId);
    List<SysPermission> findPermissionByPid(@Param("pid") Integer pid);
}
