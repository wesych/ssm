package org.wesc.ssm.utils.demo;

/**
 * 线程安全的单例模式demo.
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/8/24 16:47
 */
public class Singleton {

    /** 私有构造方法，防止被实例化 */
    private Singleton() {
    }

    /** 此处使用一个内部类来维护单例 */
    private static class SingletonFactory {
        public static final Singleton instance = new Singleton();
    }

    /**
     * 获取对象实例
     * @return
     */
    public Singleton getInstance() {
        return SingletonFactory.instance;
    }

    /** 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
}
