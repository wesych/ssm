package org.wesc.ssm.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/7/31 10:53
 */
@Controller
@RequestMapping("/vue")
public class VueController {

    @RequestMapping("/user")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("vue/user");
        return mv;
    }
}
