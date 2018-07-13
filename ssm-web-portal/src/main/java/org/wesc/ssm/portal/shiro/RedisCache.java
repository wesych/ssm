package org.wesc.ssm.portal.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Description:
 * @Auther: Wesley Cheung
 * @Date: 2018/7/13 14:00
 */
public class RedisCache<K,V> implements Cache<K,V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The wrapped Jedis instance.
     */
    private RedisClient redisClient;

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_cache:";

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 通过一个JedisManager实例构造RedisCache
     */
    public RedisCache(RedisClient redisClient) {
        if (redisClient == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.redisClient = redisClient;
    }

    /**
     * Constructs a redisClient instance with the specified
     * Redis manager and using a custom key prefix.
     * @param redisClient The cache manager instance
     * @param prefix The Redis key prefix
     */
    public RedisCache(RedisClient redisClient, String prefix) {

        this(redisClient);

        // set the prefix
        this.keyPrefix = prefix;
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        } else if(key instanceof PrincipalCollection){
            String preKey = this.keyPrefix + key.toString();
            return preKey.getBytes();
        }else{
            return SerializeUtil.serialize(key);
        }
    }

    @Override
    public V get(K key) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                byte[] rawValue = redisClient.get(getByteKey(key));
                @SuppressWarnings("unchecked") V value = (V) SerializeUtil.deserialize(rawValue);
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }

    }
    public String getStr(String key) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                return redisClient.get(key);
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }

    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("根据key从存储 key [" + key + "]");
        try {
            redisClient.set(getByteKey(key), SerializeUtil.serialize(value));
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public String putString(String key, String value) throws CacheException {
        logger.debug("根据key从存储 key [" + key + "]");
        try {
            redisClient.set(key, value);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public String put(String key,String value, int expire) throws CacheException {
        logger.debug("根据key从存储 key [" + key + "]");
        try {
            redisClient.set(key, value, expire);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    public String removeString(String key) throws CacheException {
        logger.debug("从redis中删除 key [" + key + "]");
        try {
            String previous = redisClient.get(key);
            redisClient.del(key);
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("从redis中删除 key [" + key + "]");
        try {
            V previous = get(key);
            redisClient.del(getByteKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("从redis中删除所有元素");
        try {
            redisClient.flushDB();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        try {
            Long longSize = new Long(redisClient.dbSize());
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        try {
            Set<byte[]> keys = redisClient.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<>();
                for (byte[] key : keys) {
                    newKeys.add((K) key);
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
            Set<byte[]> keys = redisClient.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<>(keys.size());
                for (byte[] key : keys) {
                    @SuppressWarnings("unchecked") V value = get((K) key);
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
