package org.wesc.ssm.shiro.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/7/25 17:37
 */
@Service
public class LoginService {

    public static final Logger logger = LogManager.getLogger(LoginService.class);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param rememberme
     * @return
     */
    public LoginResult doLogin(String username, String password, boolean rememberme) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
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


}
