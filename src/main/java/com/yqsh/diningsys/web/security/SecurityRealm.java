package com.yqsh.diningsys.web.security;

import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRole;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import com.yqsh.diningsys.web.service.base.SysRoleService;
import com.yqsh.diningsys.web.service.base.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户身份验证,授权 Realm 组件
 **/
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 权限检查
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = String.valueOf(principals.getPrimaryPrincipal());

        final SysUser sysUser = userService.selectByUsername(username);
        Map param = new HashMap();
        param.put("empDuties",sysUser.getEmpDuties());
        final List<SysMenu> allMenus =  sysMenuService.selectLoginUserDutiesPermission(param);
        for (SysMenu allPermisson : allMenus) {
            authorizationInfo.addStringPermission(allPermisson.getMenuCode());
        }
        return authorizationInfo;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = String.valueOf(token.getPrincipal());
        String password = new String((char[]) token.getCredentials());
        final SysUser authentication = userService.authentication(new SysUser(username, password));
        if (authentication == null) {
            throw new AuthenticationException("用户名或密码错误.");
        }
        if (!authentication.getState().equalsIgnoreCase("normal")){
            throw new AuthenticationException("该用户已被锁定，请联系管理员！");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }

}
