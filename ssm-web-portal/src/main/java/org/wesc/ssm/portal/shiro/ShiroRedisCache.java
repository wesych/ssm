package org.wesc.ssm.portal.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.wesc.ssm.dao.cache.Fastjson2JsonRedisSerializer;
import org.wesc.ssm.dao.cache.RedisTemplateUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义缓存类：使用redis来管理shiro的权限信息
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/13 14:00
 */
public class ShiroRedisCache<K,V> implements Cache<K,V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 过期时间(分钟) */
    private static final int CACHE_EXPIRE_TIME = 60;

    @Autowired
    private Fastjson2JsonRedisSerializer fastjson2JsonRedisSerializer;

    /**
     * 获得String型的key
     * @param key
     * @return
     */
    private String createStringKey(K key) {
        if (key instanceof String) {
            return ShiroRedisKey.createShiroCacheKey((String)key);
        } else if(key instanceof PrincipalCollection){
            return ShiroRedisKey.createShiroCacheKey(key.toString());
        }else{
            return ShiroRedisKey.createShiroCacheKey(new String(fastjson2JsonRedisSerializer.serialize(key)));
        }
    }

    @Override
    public V get(K key) throws CacheException {
        logger.debug("Get value from redis where key = " + key);
        try {
            if (key == null) {
                return null;
            } else {
                Object object = RedisTemplateUtil.valueGet(createStringKey(key));
                V value = (V) object;
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }

    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("Save value = " + value + "to redis with key = " + key);
        try {
            RedisTemplateUtil.valueSet(createStringKey(key), value, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("Delete value in redis where key = " + key);
        try {
            V previous = get(key);
            RedisTemplateUtil.delete(createStringKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        try {
            RedisTemplateUtil.getRedisTemp().execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    logger.debug("flush redis");
                    redisConnection.flushDb();
                    return null;
                }
            });
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        try {
            return RedisTemplateUtil.keys(ShiroRedisKey.SHIRO_CACHE_KEY_PREFIX + "*").size();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Set<K> keys() {
        try {
            Set<String> keys = RedisTemplateUtil.keys(ShiroRedisKey.SHIRO_CACHE_KEY_PREFIX + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<>();
                for (String key : keys) {
                    newKeys.add((K) key.getBytes());
                }
                return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        try {
            Set<String> keys = RedisTemplateUtil.keys(ShiroRedisKey.SHIRO_CACHE_KEY_PREFIX + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<>(keys.size());
                for (String key : keys) {
                    V value = get((K) key.getBytes());
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

}
