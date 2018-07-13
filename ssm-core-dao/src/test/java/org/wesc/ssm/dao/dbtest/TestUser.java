package org.wesc.ssm.dao.dbtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.entity.UserExample;
import org.wesc.ssm.dao.mapper.UserMapper;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mysql.xml", "classpath:spring-redis-standalone.xml"})
public class TestUser {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> users = userMapper.selectByExample(new UserExample());
        for(User user : users){
            System.err.println(user.toString());
        }
    }

    @Test
    public void testFindOne(){
        User user = userMapper.selectByPrimaryKey(1);
        System.err.println(user.toString());
    }

}
