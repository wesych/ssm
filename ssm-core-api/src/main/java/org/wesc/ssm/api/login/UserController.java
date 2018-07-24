package org.wesc.ssm.api.login;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wesc.ssm.api.base.APIResponse;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.user.UserService;

import java.util.List;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/7/24 13:57
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/listAll")
    @ResponseBody
    public APIResponse listAll() {
        List<User> users = userService.findAllUser();
        JSONObject resp = new JSONObject();
        resp.put("data", users);
        return APIResponse.createSuccessResponse(resp);
    }
}
