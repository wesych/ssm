package org.wesc.ssm.portal.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/druid")
public class DruidController {

    @RequestMapping("/index")
    public String index() {
        return "durid/index";
    }

}
