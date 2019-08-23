package com.exposure.exposureservice.shiro;

import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.service.SysPermissionService;
import com.exposure.exposureservice.service.SysRoleService;
import com.exposure.exposureservice.service.SysUserService;
import com.exposure.exposureservice.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 只支持JwtToken令牌类型
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 授权，(只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的)
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = Long.valueOf(principals.toString());
        System.out.println(userId);
        SysUser sysUser = sysUserService.findBaseInfoById(userId);
        if (null == sysUser) return null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> roles = sysRoleService.findRolesByUserId(userId);
        System.out.println(roles);
        info.addRoles(roles);
        // 获取当前用户的所有权限，并且通过addStringPermissions添加到simpleAuthorizationInfo当中
        // 这样当Shiro内部检查用户是否有某项权限时就会从SimpleAuthorizationInfo中拿取校验
        List<String> permissions = sysPermissionService.findPermissionByUserId(userId);
        System.out.println(permissions);
        info.addStringPermissions(permissions);
        return info;
    }


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        Claims claims = jwtUtils.parseJWT(token);
        String id = claims.getSubject();  // 解密Token
        if (null == id) {
            // Token解密失败，抛出异常
            throw new AuthenticationException("Invalid token.");
        }
        // Token解密成功，返回SimpleAuthenticationInfo对象
        return new SimpleAuthenticationInfo(id, token, getName());
    }
}
