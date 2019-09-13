package com.exposure.exposureservice.security;

import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.entity.exception.CommonException;
import com.exposure.exposureservice.enums.ErrorCode;
import com.exposure.exposureservice.service.SysPermissionService;
import com.exposure.exposureservice.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SysUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String sysUserId) throws UsernameNotFoundException {
        System.out.println("sysUserId:" + sysUserId);
        Long userId = Long.valueOf(sysUserId);
        SysUser sysUser = sysUserService.findBaseInfoById(userId);
        System.out.println(sysUser.getPassword());
        if (sysUser == null) {
            throw new CommonException(ErrorCode.USERNAMEORPASSWORD_ERROR);
        }
        List<String> permissions = sysPermissionService.findPermissionByUserId(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission: permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return new User(sysUserId, sysUser.getPassword(), authorities);
    }
}
