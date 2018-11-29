package org.wesc.ssm.shiro.service;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录结果.
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/25 17:59
 */
public class LoginResult {

    /** 是否登录成功. */
    private boolean success;

    /** 登录成功保存token，失败则为null. */
    private UsernamePasswordToken token;

    /** 登录返回码*/
    private int code;

    /** 登录返回信息. */
    private String message;

    /** Constructor. */
    public LoginResult(boolean success, UsernamePasswordToken token, int code, String message) {
        this.success = success;
        this.token = token;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UsernamePasswordToken getToken() {
        return token;
    }

    public void setToken(UsernamePasswordToken token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
