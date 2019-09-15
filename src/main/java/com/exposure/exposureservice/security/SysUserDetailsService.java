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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findBaseInfoByName(userName);
        if (sysUser == null) {
            throw new CommonException(ErrorCode.USERNAMEORPASSWORD_ERROR);
        }
        List<String> permissions = sysPermissionService.findPermissionByUserId(sysUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission: permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        User user = new User(sysUser.getUserName(), sysUser.getPassword(), authorities);
        return user;
    }
}
