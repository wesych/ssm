package org.wesc.ssm.dao.cache;

import org.wesc.ssm.dao.utils.MD5Util;

/**
 * 自定义缓存到Redis中的key
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/18 11:05
 */
public class MybatisCacheKey {

    public static final String MYBATIS_KEY_PREFIX = "mybatis_cache:";

    /**
     * 创建mybatis自定义的缓存Key.
     * (字符串-> MD5)
     * @param key
     * @return
     */
    public static String createMybatisCacheKey(String key) {
        return MYBATIS_KEY_PREFIX + MD5Util.md5(MYBATIS_KEY_PREFIX, key);
    }

}
