package org.wesc.ssm.portal.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页、登录页和注册页
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/6/21 17:52
 */
@Controller
public class HomeController {

    /**
     * 进入首页
     * @return
     */
    @RequestMapping(value = { "/","/index" })
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("home/index");
        return mv;
    }

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/signin")
    public ModelAndView accessSignIn(){
        ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            mv.setViewName("home/index");
        } else {
            mv.setViewName("home/signin");
        }
        return mv;
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
            mv.setViewName("home/index");
        } else {
            mv.setViewName("home/signup");
        }
        return mv;
    }
}
