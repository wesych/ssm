package org.wesc.ssm.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisTemplate二次封装, 使用JDK自带序列化工具.
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/18 14:11
 */
@Component
public class JdkRedisTemplateUtil extends RedisTemplateUtil {


    @Override
    @Autowired
    @Qualifier("jdkRedisTemplate")
    public void setRedisTemp(RedisTemplate<String, Object> redisTemplate) {
        redisTemp = redisTemplate;
    }

    @Override
    @Autowired
    @Qualifier("jdkRedisTemplate")
    public void setValueOps(RedisTemplate<String, Object> redisTemplate) {
        valueOps = redisTemplate.opsForValue();
    }

    @Override
    @Autowired
    @Qualifier("jdkRedisTemplate")
    public void setHashOps(RedisTemplate<String, Object> redisTemplate) {
        hashOps = redisTemplate.opsForHash();
    }

    @Override
    @Autowired
    @Qualifier("jdkRedisTemplate")
    public void setListOps(RedisTemplate<String, Object> redisTemplate) {
        listOps = redisTemplate.opsForList();
    }

    @Override
    @Autowired
    @Qualifier("jdkRedisTemplate")
    public void setSetOps(RedisTemplate<String, Object> redisTemplate) {
        setOps = redisTemplate.opsForSet();
    }

    @Override
    @Autowired
    @Qualifier("jdkRedisTemplate")
    public void setZSetOps(RedisTemplate<String, Object> redisTemplate) {
        zSetOps = redisTemplate.opsForZSet();
    }

}

