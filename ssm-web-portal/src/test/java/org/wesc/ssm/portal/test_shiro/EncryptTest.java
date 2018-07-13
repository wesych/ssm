package org.wesc.ssm.portal.test_shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.dao.mapper.UserMapper;
import org.wesc.ssm.portal.shiro.PasswordHelper;

/**
 * @author Wesley Cheung
 * @Date Created in 14:31 2017/12/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml",
        "classpath:spring-mysql.xml",
        "classpath:spring-redis-standalone.xml"
})
public class EncryptTest {

    public static final Logger LOGGER = LogManager.getLogger(EncryptTest.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Test
    public void test() {
        User user = userMapper.selectByPrimaryKey(2);
        user.setPassword("111");
        LOGGER.info("Account:" + user.getAccount() + ", pwd:" + passwordHelper.encryptPassword(user));
    }

}
