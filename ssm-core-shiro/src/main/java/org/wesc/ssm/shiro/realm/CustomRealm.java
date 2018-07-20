package org.wesc.ssm.shiro.realm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.exception.ServiceException;
import org.wesc.ssm.service.user.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Auther: Wesley Cheung
 * @Date: 2018/7/10 20:01
 */
public class CustomRealm extends AuthorizingRealm {

    private static final Logger logger = LogManager.getLogger(CustomRealm.class);

    public static final String USER_TYPE_ACCOUNT = "ACCOUNT";
    public static final String USER_TYPE_MOBILE = "MOBILE";
    public static final String USER_TYPE_EMAIL = "EMAIL";

    @Autowired
    private UserService userService;

    /**
     * 为当前用户授予角色与权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) principals.getPrimaryPrincipal();
        User user;
        try {
            user = userService.findUserByAccount(loginName);
            if(user == null) {
                user = userService.findUserByMobile(loginName);
                if (user == null) {
                    user = userService.findUserByEmail(loginName);
                    if (user == null) {
                        throw new UnknownAccountException(); //没找到帐号
                    }
                }
            }
        } catch (ServiceException e) {
            throw new UnknownAccountException(); //没找到唯一帐号
        }

        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        if (user != null) {
            roles = new HashSet(userService.findUserRolesById(user.getUserId()));             //添加角色
            permissions = new HashSet(userService.findUserPermissionsById(user.getUserId())); //添加权限
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 获取认证信息，验证当前登陆的用户
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            String loginName = token.getUsername();
            String loginField;
            User user;
            // 查找用户（账号、手机、邮箱）
            user = userService.findUserByAccount(loginName);
            if(user == null) {
                user = userService.findUserByMobile(loginName);
                if (user == null) {
                    user = userService.findUserByEmail(loginName);
                    if (user == null) {
                        throw new UnknownAccountException(); //没找到帐号
                    } else {
                        loginField = USER_TYPE_EMAIL;
                    }
                } else {
                    loginField = USER_TYPE_MOBILE;
                }
            } else {
                loginField = USER_TYPE_ACCOUNT;
            }

            if(Boolean.TRUE.equals(user.getLocked())) {
                throw new LockedAccountException();      //帐号锁定
            }

            //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
            String principle;
            if (loginField.equals(USER_TYPE_ACCOUNT)){
                principle = user.getAccount();
            } else if(loginField.equals(USER_TYPE_MOBILE)){
                principle = user.getMobile();
            } else {
                principle = user.getEmail();
            }
            logger.info("[UserLoginType:" + loginField + ", value:" + principle + "]");

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(),
                    new CustomSimpleByteSource(userService.getCredentialSalt(user)),
                    getName()
            );
            return authenticationInfo;
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

}
