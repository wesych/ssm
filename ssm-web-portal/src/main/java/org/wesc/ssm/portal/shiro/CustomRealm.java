package org.wesc.ssm.portal.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.mapper.UserMapper;

import static org.apache.commons.lang3.SystemUtils.USER_NAME;
import static org.flywaydb.core.internal.configuration.ConfigUtils.PASSWORD;

/**
 * @Description:
 * @Auther: Wesley Cheung
 * @Date: 2018/7/10 20:01
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    //这里因为没有调用后台，直接默认只有一个用户("luoguohui"，"123456")
    private static final String USERNAME = "luoguohui";
    private static final String PASSWORD = "123456";

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        /**根据用户ID查询角色（role），放入到Authorization里.*/
        //info.setRoles(adminUserService.findRoleByUserId(userId));
        /**根据用户ID查询权限（permission），放入到Authorization里.*/
        //info.setStringPermissions(adminUserService.findPermissionByUserId(userId));
        return info;
    }

    /**
     * 验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if(token.getUsername().equals(USERNAME)){
            //return new SimpleAuthenticationInfo(USERNAME, DecriptUtil.MD5(PASSWORD), getName());
            return null;
        }else{
            throw new AuthenticationException();
        }
    }

}
