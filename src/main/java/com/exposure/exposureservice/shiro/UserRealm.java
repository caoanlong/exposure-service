package com.exposure.exposureservice.shiro;

import com.exposure.exposureservice.entity.SysUser;
import com.exposure.exposureservice.service.SysPermissionService;
import com.exposure.exposureservice.service.SysUserService;
import com.exposure.exposureservice.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

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
        // 从principals中拿到Token令牌
        Long userId = Long.valueOf(JWTUtil.decode(principals.toString()));
        SysUser sysUser = sysUserService.findById(userId);
        if (null == sysUser) return null;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前用户的所有权限，并且通过addStringPermissions添加到simpleAuthorizationInfo当中
        // 这样当Shiro内部检查用户是否有某项权限时就会从SimpleAuthorizationInfo中拿取校验
        List<String> permissions = sysPermissionService.findPermissionByUserId(sysUser.getId());
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
        String id = JWTUtil.decode(token);  // 解密Token
        if (null == id) {
            // Token解密失败，抛出异常
            throw new AuthenticationException("Invalid token.");
        }
        // Token解密成功，返回SimpleAuthenticationInfo对象
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
