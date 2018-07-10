package org.wesc.ssm.dao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author Wesley Cheung
 * @Date Created in 15:22 2017/12/26
 */
public class RedisCacheTransfer {

    @Autowired
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisMybatisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
