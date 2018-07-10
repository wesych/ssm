package org.wesc.ssm.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wesc.ssm.dao.entity.Role;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.entity.UserExample;
import org.wesc.ssm.dao.entity.UserRole;
import org.wesc.ssm.dao.mapper.RolePermissionMapper;
import org.wesc.ssm.dao.mapper.UserMapper;
import org.wesc.ssm.dao.mapper.UserRoleMapper;

import java.util.List;

/**
 * @Description:
 * @Auther: Wesley Cheung
 * @Date: 2018/7/10 20:41
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public List<Role> findUserRolesById(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        List<UserRole> userRoles = userRoleMapper.selectByExample();
    }
}
