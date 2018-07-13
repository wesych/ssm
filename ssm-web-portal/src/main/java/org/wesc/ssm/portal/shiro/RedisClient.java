package org.wesc.ssm.portal.shiro;

import org.wesc.ssm.dao.cache.RedisPool;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Redis Manager
 *
 * @author Wesley Cheung
 * @Date Created in 15:02 2017/12/25
 */
public class RedisClient {

    private RedisPool redisPool;

    // 0 - never expire
    private int expire = 0;

    /**
     * Default constructor.
     */
    public RedisClient() {
    }

    public RedisPool getRedisPool() {
        return redisPool;
    }

    public void setRedisPool(RedisPool redisPool) {
        this.redisPool = redisPool;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    /**
     * Get byte[] from redis.
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        byte[] value;
        Jedis jedis = redisPool.getInstance();
        try {
            value = jedis.get(key);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * Get String from redis.
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String value;
        Jedis jedis = redisPool.getInstance();
        try {
            value = jedis.get(key);
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * Set String to redis.
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = redisPool.getInstance();
        try {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * Set byte[] to redis.
     *
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = redisPool.getInstance();
        try {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * Set String to redis with custom expire.
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public String set(String key, String value, int expire) {
        Jedis jedis = redisPool.getInstance();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * Set byte[] to redis with custom expire.
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = redisPool.getInstance();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * del string key
     *
     * @param key
     */
    public void del(String key) {
        Jedis jedis = redisPool.getInstance();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    /**
     * del byte[] key
     * @param key
     */
    public void del(byte[] key){
        Jedis jedis = redisPool.getInstance();
        try{
            jedis.del(key);
        }finally{
            jedis.close();
        }
    }

    /**
     * flush
     */
    public void flushDB() {
        Jedis jedis = redisPool.getInstance();
        try {
            jedis.flushDB();
        } finally {
            jedis.close();
        }
    }

    /**
     * size
     */
    public Long dbSize() {
        Long dbSize;
        Jedis jedis = redisPool.getInstance();
        try {
            dbSize = jedis.dbSize();
        } finally {
            jedis.close();
        }
        return dbSize;
    }

    /**
     * keys
     *
     * @param regex
     * @return
     */
    public Set<byte[]> keys(String regex) {
        Set<byte[]> keys;
        Jedis jedis = redisPool.getInstance();
        try {
            keys = jedis.keys(regex.getBytes());
        } finally {
            jedis.close();
        }
        return keys;
    }

}

