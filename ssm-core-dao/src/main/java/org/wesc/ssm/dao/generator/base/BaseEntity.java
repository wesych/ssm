package org.wesc.ssm.dao.generator.base;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;

import com.alibaba.fastjson.JSONObject;

public abstract class BaseEntity<ID> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回ID属性的值.
     * @return ID属性的值
     */
    public abstract ID getIdValue();
    
    /**
     * 设置ID属性的值.
     * @param id ID值
     */
    public abstract void setIdValue(ID id);
    
    /**
     * 获取ID属性的属性名.
     * @return ID属性名
     */
    public abstract String getIdPropertyName();
    
    /**
     * 将当前实体类转为JSONObject.
     * @return JSONObject
     */
    public abstract JSONObject toJSON();

    @Override
    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getClass().getSimpleName());
            sb.append(" { ");

            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(this.getClass());
            for (int i = 0; i < pds.length; i++) {
                PropertyDescriptor pd = pds[i];
                
                // getClass不处理
                if ("class".equals(pd.getName())) {
                    continue;
                }

                // 判断字段是否为空
                if (pd.getReadMethod() != null) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(pd.getName());
                    sb.append("=");
                    sb.append(pd.getReadMethod().invoke(this));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field values.", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        try {
            // 被判断的对象为null，不相等
            if (obj == null) {
                return false;
            }

            // 不同类型，不相等
            if (!obj.getClass().equals(this.getClass())) {
                return false;
            }

            // 判断属性
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(this.getClass());
            for (PropertyDescriptor pd : pds) {
                // getClass不处理
                if ("class".equals(pd.getName())) {
                    continue;
                }
                
                // 获取字段值
                Object value1 = pd.getReadMethod().invoke(obj);
                Object value2 = pd.getReadMethod().invoke(this);
                
                // 比较字段
                if (value1 == null && value2 == null) {
                    continue;
                }
                if (value1 != null && value2 == null || value1 == null && value2 != null) {
                    return false;
                }
                if (!value1.equals(value2)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field values.", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            // 创建新实例
            BaseEntity<ID> newEntity = this.getClass().newInstance();

            // 复制属性
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(this.getClass());
            for (PropertyDescriptor pd : pds) {
                // getClass不处理
                if ("class".equals(pd.getName())) {
                    continue;
                }
                
                // 获取get和set方法
                Method getter = pd.getReadMethod();
                Method setter = pd.getWriteMethod();
                if (getter == null || setter == null) {
                    continue;
                }
                
                // 复制值
                setter.invoke(newEntity, getter.invoke(this));
            }
            return newEntity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get field values.", e);
        }
    }

    @Override
    public int hashCode() {
        try {
            // Hash结果
            int hash = 0x12345678;

            // 复制属性
            PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(this.getClass());
            for (PropertyDescriptor pd : pds) {
                // getClass不处理
                if ("class".equals(pd.getName())) {
                    continue;
                }
                
                // 获取值
                Object value = pd.getReadMethod().invoke(this);
                if (value != null) {
                    hash = hash & value.hashCode();
                }
            }
            return hash;
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hash code.", e);
        }
    }
}
