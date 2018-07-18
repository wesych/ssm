package org.wesc.ssm.portal.shiro;

import org.wesc.ssm.dao.utils.MD5Util;

/**
 * 自定义缓存到Redis中的key
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/18 11:05
 */
public class ShiroRedisKey {

    public static final String SHIRO_CACHE_KEY_PREFIX = "shiro_cache_";

    public static final String SHIRO_SESSION_KEY_PREFIX = "shiro_session_";

    /**
     * 创建shiro自定义的cache Key.
     * (字符串-> MD5)
     * @param key
     * @return
     */
    public static String createShiroCacheKey(String key) {
        return SHIRO_CACHE_KEY_PREFIX + MD5Util.md5(SHIRO_CACHE_KEY_PREFIX, key);
    }

    /**
     * 创建shiro自定义的session Key.
     * (字符串-> MD5)
     * @param key
     * @return
     */
    public static String createShiroSessionKey(String key) {
        return SHIRO_SESSION_KEY_PREFIX + MD5Util.md5(SHIRO_SESSION_KEY_PREFIX, key);
    }

}
