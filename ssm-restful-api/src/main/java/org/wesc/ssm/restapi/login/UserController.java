package org.wesc.ssm.restapi.login;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.restapi.base.APIResponse;
import org.wesc.ssm.service.user.UserService;

import java.util.List;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/7/24 13:57
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public APIResponse listAll() {
        List<User> users = userService.findAllUser();

        JSONObject resp = new JSONObject();
        resp.put("data", users);
        resp.put("count", users.size());
        return APIResponse.createSuccessResponse(resp);
    }
}
