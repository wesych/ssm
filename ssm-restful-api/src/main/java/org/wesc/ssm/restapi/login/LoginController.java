package org.wesc.ssm.restapi.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wesc.ssm.restapi.base.APIResponse;
import org.wesc.ssm.restapi.interceptor.AntiRepeat;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.exception.ServiceException;
import org.wesc.ssm.service.user.UserService;
import org.wesc.ssm.shiro.service.LoginResult;
import org.wesc.ssm.shiro.service.SignService;

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
    private SignService signService;

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
        LoginResult result = signService.signIn(username, password, rememberme);
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
            User user = signService.signUp(nickname, mobile, password);
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
    public APIResponse logout() {
        try {
            signService.logout();
            return APIResponse.createSuccessResponse();
        } catch (ServiceException e) {
            return APIResponse.createFailResponse(e.getMessage());
        }
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
        if (!StringUtils.isEmpty(account) && userService.findAllAccounts().contains(account)){
            return APIResponse.createFailResponse("account exists");
        }
        if (!StringUtils.isEmpty(mobile) && userService.findAllMobiles().contains(mobile)){
            return APIResponse.createFailResponse("mobile exists");
        }
        if (!StringUtils.isEmpty(email) && userService.findAllEmails().contains(email)){
            return APIResponse.createFailResponse("email exists");
        }
        return APIResponse.createSuccessResponse();
    }
}
