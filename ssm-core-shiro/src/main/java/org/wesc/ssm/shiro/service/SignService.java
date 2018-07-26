package org.wesc.ssm.shiro.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.exception.ServiceException;
import org.wesc.ssm.service.user.UserService;
import org.wesc.ssm.shiro.realm.PasswordHelper;
import org.wesc.ssm.utils.tool.RandomIdentity;

/**
 * 用户登录、注册、退出相关的服务类.
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/25 17:37
 */
@Service
public class SignService {

    public static final Logger logger = LogManager.getLogger(SignService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param rememberme
     * @return
     */
    public LoginResult signIn(String username, String password, boolean rememberme) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberme);
            try {
                currentUser.login(token);
                String msg = "用户" + username + "登录成功";
                logger.info(msg);
                return new LoginResult(true, token, msg);
            } catch (UnknownAccountException ex) {
                String errMsg = "账号不存在";
                logger.info(errMsg);
                return new LoginResult(false, null, errMsg);
            } catch (IncorrectCredentialsException ex) {
                String errMsg = "密码错误";
                logger.info(errMsg);
                return new LoginResult(false, null, errMsg);
            } catch (LockedAccountException ex) {
                String errMsg = username + "：此账号已被锁定";
                logger.info(errMsg);
                return new LoginResult(false, null, errMsg);
            } catch (AuthenticationException ex) {
                String errMsg = "没有权限";
                logger.info(errMsg);
                return new LoginResult(false, null, errMsg);
            }
        } else {
            String errMsg = "系统故障：已验证用户无法再次登录或注册";
            logger.info(errMsg);
            return new LoginResult(false, null, errMsg);
        }
    }


    /**
     * 用户注册.
     *
     * @param nickname
     * @param mobile
     * @param password
     */
    public User signUp(String nickname, String mobile, String password) throws ServiceException {
        // parameter check
        if (StringUtils.isEmpty(nickname)) {
            throw new ServiceException("nickname can not be null", "注册昵称不能为空");
        }
        if (StringUtils.isEmpty(mobile) || null != userService.findUserByMobile(mobile)) {
            throw new ServiceException("mobile can not be null and mobile should be unique", "注册手机号为空或已存在");
        }
        if (StringUtils.isEmpty(password)) {
            throw new ServiceException("password can not be null", "注册密码不能为空");
        }

        try {
            String account = "ssm_" + RandomIdentity.createRandomNumeric(10);
            while (userService.findAllAccounts().contains(account)) {
                account = "ssm_" + RandomIdentity.createRandomNumeric(10);
            }
            User user = new User();
            user.setAccount(account);
            user.setMobile(mobile);
            user.setPassword(password);
            user.setNickname(nickname);
            passwordHelper.encryptPassword(user);
            userService.addUser(user);
            return userService.findUserByMobile(mobile);
        } catch (Exception e) {
            throw new ServiceException("SignUp error", "注册失败");
        }
    }

    /**
     * 注销登录信息.
     * @throws ServiceException
     */
    public void logout() throws ServiceException {
        try {
            Subject subject = SecurityUtils.getSubject();
            logger.info("用户" + ((User)subject.getPrincipal()).getAccount() + "注销登录");
            subject.logout();
        } catch (Exception e) {
            throw new ServiceException("Logout error", "注销失败");
        }
    }

}
