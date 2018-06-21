package org.wesc.ssm.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:首页
 * @Auther: Wesley Cheung
 * @Date: 2018/6/21 17:52
 */
@Controller
public class HomeController {

    @RequestMapping(value = { "/","/index" })
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}
