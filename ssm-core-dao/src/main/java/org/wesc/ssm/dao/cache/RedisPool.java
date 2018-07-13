package org.wesc.ssm.dao.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 * @author Wesley Cheung
 * @Date Created in 15:29 2017/12/25
 */
public class RedisPool {

    public static final Logger logger = LogManager.getLogger(RedisPool.class);

    /**
     * Pool Configuration.
     */
    private JedisPoolConfig jedisPoolConfig;

    /**
     * RedisStandaloneConfiguration
     */
    private RedisStandaloneConfiguration standaloneConfiguration;

    /**
     * Timeout.
     */
    private int timeout;

    /**
     * Pool.
     */
    private JedisPool pool = null;

    /**
     * Initialize.
     * TODO: 单节点配置的password处理
     */
    public void init() {
        if (timeout == 0) {
            pool = new JedisPool(jedisPoolConfig, standaloneConfiguration.getHostName(), standaloneConfiguration.getPort());
        } else {
            pool = new JedisPool(jedisPoolConfig, standaloneConfiguration.getHostName(), standaloneConfiguration.getPort(), timeout);
        }
    }

    public Jedis getInstance() {
        return pool.getResource();
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public RedisStandaloneConfiguration getStandaloneConfiguration() {
        return standaloneConfiguration;
    }

    public void setStandaloneConfiguration(RedisStandaloneConfiguration standaloneConfiguration) {
        this.standaloneConfiguration = standaloneConfiguration;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
