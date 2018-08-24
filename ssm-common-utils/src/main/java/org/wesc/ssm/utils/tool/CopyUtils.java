package org.wesc.ssm.utils.tool;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/8/24 17:06
 */
@Slf4j
public class CopyUtils {

    /**
     * 将父类的属性全部(serialVersionUID排除)拷贝到子类中
     *
     * @param parent
     * @param child
     */
    public static void copyParentToChild(Object parent, Object child) {
        if (!(child.getClass().getSuperclass() == parent.getClass())) {
            log.error("child不是parent的子类", "child不是parent的子类");
            return;
        }
        Class<? extends Object> fatherClass = parent.getClass();
        Field ff[] = fatherClass.getDeclaredFields();
        for (int i = 0; i < ff.length; i++) {
            Field f = ff[i];
            if (f.getName().equals("serialVersionUID")) {
                continue;
            }
            try {
                Method m = fatherClass.getMethod("get" + upperHeadChar(f.getName()));
                Object obj = m.invoke(parent);
                f.setAccessible(true);
                f.set(child, obj);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 首字母大写，in:deleteDate，out:DeleteDate
     */
    private static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1);
        return out;
    }


    /**
     * 深复制
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T deepCopy(T t) {
        T cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(t);
            obs.close();

            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }
}
