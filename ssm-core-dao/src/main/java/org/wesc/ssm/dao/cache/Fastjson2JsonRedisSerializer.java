package org.wesc.ssm.dao.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * 使用Fastjson对数据对象进行序列化
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/18 14:11
 */
public class Fastjson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    /** 打开AutoType功能 */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public Fastjson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }


    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize. Can be {@literal null}.
     * @return the equivalent binary data. Can be {@literal null}.
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONStringWithDateFormat(t, JSON.DEFFAULT_DATE_FORMAT, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }
}
