package org.wesc.ssm.portal.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.shiro.PasswordHelper;
import org.wesc.ssm.service.user.UserService;
import org.wesc.ssm.utils.tool.RandomIdentity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wesley Cheung
 * @Date Created in 13:52 2017/12/20
 */
@Controller("signController")
@Scope("prototype")
public class SignController {

    public static final Logger logger = LogManager.getLogger(SignController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/signin")
    public ModelAndView accessSignIn(){
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            mv.setViewName("public/index");
        } else {
            mv.setViewName("public/signin");
        }
        return mv;
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("/doSignIn")
    @ResponseBody
    public AjaxResponse doSignIn(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("rememberme") boolean rememberme) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(rememberme);
            try {
                currentUser.login(token);
                logger.info("用户" + username + "登录成功");
                return AjaxResponse.createSuccessResponse();
            } catch (UnknownAccountException ex) {
                logger.info("账号不存在");
                return AjaxResponse.createFailResponse("ACCOUNT");
            } catch (IncorrectCredentialsException ex) {
                logger.info("密码错误");
                return AjaxResponse.createFailResponse("PASSWORD");
            } catch (LockedAccountException ex) {
                logger.info(username + "：此账号已被锁定");
                return AjaxResponse.createFailResponse("LOCKED");
            } catch (AuthenticationException ex) {
                logger.info("没有权限");
                return AjaxResponse.createFailResponse("AUTH");
            }
        } else {
            logger.info("系统故障：已验证用户无法再次登录或注册");
            return AjaxResponse.createFailResponse("SYSTEM BUG FOUND");
        }
    }

    /**
     * 进入注册页面
     * @return
     */
    @RequestMapping("/signup")
    public ModelAndView accessSignUp(){
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            mv.setViewName("public/index");
        } else {
            mv.setViewName("public/signup");
        }
        return mv;
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/doSignUp")
    @ResponseBody
    public AjaxResponse doSignUp(
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

            return AjaxResponse.createSuccessResponse(user);
        } catch (Exception e) {
            return AjaxResponse.createFailResponse(e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value={"/logout"})
    @ResponseBody
    public AjaxResponse logout(){
        Subject subject = SecurityUtils.getSubject();
        logger.info("用户" + ((User)subject.getPrincipal()).getAccount() + "退出登录");
        subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        return AjaxResponse.createSuccessResponse();
    }

    /**
     * 唯一性检测：账号、手机号、邮箱
     * @return
     */
    @RequestMapping(value={"/checkUniqueness"})
    @ResponseBody
    public AjaxResponse checkUniqueness(HttpServletRequest request){
        String account = request.getParameter("account");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        if (account != null && userService.findAllAccounts().contains(account)){
            return AjaxResponse.createFailResponse("account exists", "ACCOUNT");
        }
        if (mobile != null && userService.findAllMobiles().contains(mobile)){
            return AjaxResponse.createFailResponse("mobile exists", "MOBILE");
        }
        if (email != null && userService.findAllEmails().contains(email)){
            return AjaxResponse.createFailResponse("email exists", "EMAIL");
        }
        return AjaxResponse.createSuccessResponse();
    }
}
