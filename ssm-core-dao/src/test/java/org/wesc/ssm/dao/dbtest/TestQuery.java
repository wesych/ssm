package org.wesc.ssm.dao.dbtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wesc.ssm.dao.entity.Permission;
import org.wesc.ssm.dao.entity.Role;
import org.wesc.ssm.dao.querymapper.UserRolesAndPermissionsQueryMapper;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mysql.xml", "classpath:spring-redis-standalone.xml"})
public class TestQuery {

    @Autowired
    private UserRolesAndPermissionsQueryMapper userRolesAndPermissionsQueryMapper;

    @Test
    public void testQueryRoles(){
        List<Role> roles = userRolesAndPermissionsQueryMapper.findAllRolesByUserId(2);
        for(Role role : roles){
            System.err.println(role.toString());
        }
    }

    @Test
    public void testQueryPermissions(){
        List<Permission> permissions = userRolesAndPermissionsQueryMapper.findAllPermissionsByUserId(2);
        for(Permission p : permissions){
            System.err.println(p.toString());
        }
    }
}
