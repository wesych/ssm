package org.wesc.ssm.shiro.service;

/**
 * @Disp: Shiro登录信息常量
 * @Author: zhangwei
 * @Date: 2018/11/29 14:58
 */
public interface LoginMessage {

    class ShiroInfo {
        private int code;

        private String msg;

        public ShiroInfo(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    ShiroInfo SUCCESS = new ShiroInfo(1000, "登录成功");

    ShiroInfo ACCOUNT = new ShiroInfo(1001, "账号不存在");

    ShiroInfo PASSWORD = new ShiroInfo(1002, "密码错误");

    ShiroInfo LOCKED = new ShiroInfo(1003, "账号已被锁定");

    ShiroInfo AUTH = new ShiroInfo(1004, "没有权限");

    ShiroInfo SYSTEM = new ShiroInfo(1005, "已验证用户无法再次登录或注册");

}
