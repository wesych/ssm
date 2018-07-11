package org.wesc.ssm.dao.utils;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Mybatis second cache using redis.
 *
 * @author Wesley Cheung
 * @Date Created in 16:56 2017/12/25
 */
public class RedisMybatisCache implements Cache {

    public static final Logger logger = LogManager.getLogger(RedisMybatisCache.class);

    private static final String KEY_PREFIX = "mybatis_redis_session:";

    /**
     * Cache Id.
     */
    private final String id;

    private static JedisConnectionFactory jedisConnectionFactory;

    // for value
    private RedisSerializer jsonSerializer = new FastJson2JsonRedisSerializer(Object.class);

    /**
     * The ReadWriteLock.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * Constructor.
     *
     * @param id
     */
    public RedisMybatisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.info("MybatisRedisCache id=" + id);
        this.id = id;
    }

    /**
     * 静态注入：Transfer中调用
     * @param jedisConnectionFactory
     */
    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisMybatisCache.jedisConnectionFactory = jedisConnectionFactory;
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(Object key) {
        if (key instanceof String) {
            String preKey = KEY_PREFIX + key;
            return preKey.getBytes();
        }
        return SerializeUtil.serialize(key);
    }


    /**
     * @return The identifier of this cache
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * Put.
     *
     * @param key   Can be any object but usually it is a {@link CacheKey}
     * @param value The result of a select.
     */
    @Override
    public void putObject(Object key, Object value) {
        logger.info(">>>Mybatis Put [" + key + ", " + value + "]");
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            connection.set(getByteKey(key), jsonSerializer.serialize(value));
        } catch (JedisConnectionException e) {
            logger.error("Put Error:" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Get.
     *
     * @param key The key
     * @return The object stored in the cache.
     */
    @Override
    public Object getObject(Object key) {
        Object result = null;
        JedisConnection connection = null;
        try {
            connection = (JedisConnection)jedisConnectionFactory.getConnection();
            result = jsonSerializer.deserialize(connection.get(getByteKey(key)));
            logger.info(">>>Mybatis Get [" + key + ", " + result + "]");
        } catch (JedisConnectionException e) {
            logger.error("Get Error:" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * Remove.
     *
     * @param key The key
     * @return Not used
     */
    @Override
    public Object removeObject(Object key) {
        logger.info(">>>Mybatis Remove key:" + key);
        JedisConnection connection = null;
        Object result = null;
        try {
            connection = (JedisConnection)jedisConnectionFactory.getConnection();
            result = connection.expire(getByteKey(key), 0);
        } catch (JedisConnectionException e) {
            logger.error("Remove Error:" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * Clears this cache instance.
     */
    @Override
    public void clear() {
        logger.info(">>>Mybatis clear all keys");
        JedisConnection connection = null;
        try {
            connection = (JedisConnection)jedisConnectionFactory.getConnection();
            connection.flushDb();
            connection.flushAll();
        } catch (JedisConnectionException e) {
            logger.error("Clear Error:" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Get size.
     *
     * @return The number of elements stored in the cache (not its capacity).
     */
    @Override
    public int getSize() {
        logger.info(">>>Mybatis get the total number of available keys");
        int result = 0;
        JedisConnection connection = null;
        try {
            connection = (JedisConnection)jedisConnectionFactory.getConnection();
            result = Integer.valueOf(connection.dbSize().toString());
        } catch (JedisConnectionException e) {
            logger.error("Get Size Error:" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    /**
     * @return ReadWriteLock
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

}
