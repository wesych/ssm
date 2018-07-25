package org.wesc.ssm.api.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wesc.ssm.api.base.APIResponse;
import org.wesc.ssm.api.interceptor.AntiRepeat;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.user.UserService;
import org.wesc.ssm.shiro.realm.PasswordHelper;
import org.wesc.ssm.shiro.service.LoginResult;
import org.wesc.ssm.shiro.service.LoginService;
import org.wesc.ssm.utils.tool.RandomIdentity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wesley Cheung
 * @Date Created in 13:52 2017/12/20
 */
@Controller
public class LoginController {

    public static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @return
     */
    @RequestMapping("/doSignIn")
    @ResponseBody
    @AntiRepeat
    public APIResponse doSignIn(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("rememberme") boolean rememberme) {
        LoginResult result = loginService.doLogin(username, password, rememberme);
        if (result.isSuccess()) {
            return APIResponse.createSuccessResponse(result.getToken());
        } else {
            return APIResponse.createFailResponse(result.getMessage());
        }
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/doSignUp")
    @ResponseBody
    @AntiRepeat
    public APIResponse doSignUp(
            @RequestParam("nickname") String nickname,
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password){
        try {
            String account = "jy_" + RandomIdentity.createRandomNumeric(10);
            while (userService.findAllAccounts().contains(account)) {
                account = "jy_" + RandomIdentity.createRandomNumeric(10);
            }
            User user = new User();
            user.setAccount(account);
            user.setMobile(mobile);
            user.setPassword(password);
            user.setNickname(nickname);
            passwordHelper.encryptPassword(user);

            userService.addUser(user);

            return APIResponse.createSuccessResponse(user);
        } catch (Exception e) {
            return APIResponse.createFailResponse(e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value={"/logout"})
    @ResponseBody
    public APIResponse logout(){
        Subject subject = SecurityUtils.getSubject();
        logger.info("用户" + ((User)subject.getPrincipal()).getAccount() + "退出登录");
        subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        return APIResponse.createSuccessResponse();
    }

    /**
     * 唯一性检测：账号、手机号、邮箱
     * @return
     */
    @RequestMapping(value={"/checkUniqueness"})
    @ResponseBody
    public APIResponse checkUniqueness(HttpServletRequest request){
        String account = request.getParameter("account");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        if (account != null && userService.findAllAccounts().contains(account)){
            return APIResponse.createFailResponse("account exists");
        }
        if (mobile != null && userService.findAllMobiles().contains(mobile)){
            return APIResponse.createFailResponse("mobile exists");
        }
        if (email != null && userService.findAllEmails().contains(email)){
            return APIResponse.createFailResponse("email exists");
        }
        return APIResponse.createSuccessResponse();
    }
}
