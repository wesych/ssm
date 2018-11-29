package org.wesc.ssm.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 自定义cacheManager, 使用redis作缓存
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/13 14:16
 */
public class ShiroRedisCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCacheManager.class);

    /** fast lookup by name map */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("Get shiro cache instance where name = " + name);

        Cache cache = caches.get(name);
        if (null == cache) {
            // create a new cache instance
            cache = new ShiroRedisCache<K, V>();
            // add it to the cache collection
            caches.put(name, cache);
        }
        return cache;
    }

}
