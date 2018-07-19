package org.wesc.ssm.dao.cache;

import org.apache.ibatis.cache.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: Mybatis二级缓存类
 * @Auther: Wesley Cheung
 * @Date: 2018/7/17 18:32
 */
public class MybatisRedisCache implements Cache {

    private static final Logger logger = LogManager.getLogger(MybatisRedisCache.class);

    /** The ReadWriteLock. */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    /**
     * 过期时间（分钟）
     */
    private static final long MYBATIS_CACHE_EXPIRE_TIME = 30;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.error(">>>>>>>>>>>>>>>>>>>>>>>>MybatisRedisCache:id=" + id);
        this.id = id;
    }

    /**
     * @return The identifier of this cache
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * @param key   Can be any object.
     * @param value The result of a select.
     */
    @Override
    public void putObject(Object key, Object value) {
        if(null != value){
            FastjsonRedisTemplateUtil.valueSet(MybatisCacheKey.createMybatisCacheKey(key.toString()), value, MYBATIS_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            logger.debug("Put value to redis:" + value);
        }
    }

    /**
     * @param key The key
     * @return The object stored in the cache.
     */
    @Override
    public Object getObject(Object key) {
        logger.debug("Get cached value from redis where key = " + key);
        Object object = FastjsonRedisTemplateUtil.valueGet(MybatisCacheKey.createMybatisCacheKey(key.toString()));
        return object;
    }

    /**
     * As of 3.3.0 this method is only called during a rollback
     * for any previous value that was missing in the cache.
     * This lets any blocking cache to release the lock that
     * may have previously put on the key.
     * A blocking cache puts a lock when a value is null
     * and releases it when the value is back again.
     * This way other threads will wait for the value to be
     * available instead of hitting the database.
     *
     * @param key The key
     * @return Not used
     */
    @Override
    public Object removeObject(Object key) {
        logger.debug("Remove cached value from redis where key = " + key);
        FastjsonRedisTemplateUtil.delete(MybatisCacheKey.createMybatisCacheKey(key.toString()));
        return key;
    }

    /**
     * Clears this cache instance
     */
    @Override
    public void clear() {
        FastjsonRedisTemplateUtil.getRedisTemp().execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                logger.debug("flush redis");
                redisConnection.flushDb();
                return null;
            }
        });
    }

    /**
     * Optional. This method is not called by the core.
     *
     * @return The number of elements stored in the cache (not its capacity).
     */
    @Override
    public int getSize() {
        String pattern = "[\\s\\S]*";
        return FastjsonRedisTemplateUtil.keys(pattern).size();
    }

    /**
     * Optional. As of 3.2.6 this method is no longer called by the core.
     * <p>
     * Any locking needed by the cache must be provided internally by the cache provider.
     *
     * @return A ReadWriteLock
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
