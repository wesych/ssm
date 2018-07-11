package org.wesc.ssm.portal.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 解决：
 * shiro 使用缓存时出现：java.io.NotSerializableException: org.apache.shiro.util.SimpleByteSource
 * 序列化后，无法反序列化的问题
 *
 * @author Wesley Cheung
 * @Date Created in 14:49 2017/12/29
 */
public class CustomSimpleByteSource implements ByteSource, Serializable {
    private static final long serialVersionUID = 5175082362119580768L;

    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public CustomSimpleByteSource() {
    }

    public CustomSimpleByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public CustomSimpleByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public CustomSimpleByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public CustomSimpleByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }

    public CustomSimpleByteSource(File file) {
        this.bytes = (new CustomSimpleByteSource.BytesHelper()).getBytes(file);
    }

    public CustomSimpleByteSource(InputStream stream) {
        this.bytes = (new CustomSimpleByteSource.BytesHelper()).getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }


    @Override
    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }
        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }

        return this.cachedBase64;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public String toString() {
        return this.toBase64();
    }

    public int hashCode() {
        return this.bytes != null && this.bytes.length != 0 ? Arrays.hashCode(this.bytes) : 0;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ByteSource) {
            ByteSource bs = (ByteSource) o;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }

}
