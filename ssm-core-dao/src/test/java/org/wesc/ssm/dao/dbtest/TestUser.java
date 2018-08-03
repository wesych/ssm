package org.wesc.ssm.dao.dbtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.mapper.UserMapper;
import org.wesc.ssm.dao.utils.MyBatisUtils;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mysql.xml", "classpath:spring-redis-sentinel.xml"})
public class TestUser {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> users = userMapper.selectByMap(MyBatisUtils.EMPTY_MAP);
        users.forEach(System.err::println);
    }

    @Test
    public void testFindOne(){
        User user = userMapper.selectById(1);
        System.err.println(user.toString());
    }

}
