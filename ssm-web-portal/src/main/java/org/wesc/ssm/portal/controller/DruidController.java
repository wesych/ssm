package org.wesc.ssm.portal.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/druid")
public class DruidController {

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("durid/index");
    }

}
