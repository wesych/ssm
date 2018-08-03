package org.wesc.ssm.dao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * MyBatis工具类.
 */
public class MyBatisUtils
{
    
    /**
     * 空map.
     */
    public static final Map<String, Object> EMPTY_MAP = new TreeMap<>();

    /**
     * 构造参数Map.
     * buildParameterMap("name1", value1, "name2", value2, ...) 
     * @param objs 参数名称和值
     * @return     参数Map，用于传递给MyBatis Mapper
     */
    public static Map<String, Object> buildParameterMap(Object... objs) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < objs.length / 2; i++) {
            String key = (String) objs[i * 2];
            Object value = objs[i * 2 + 1];
            if (value != null) {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 构造参数Map，如果某项值为null，忽略该项.
     * buildParameterMap("name1", value1, "name2", value2, ...) 
     * @param objs 参数名称和值
     * @return     参数Map，用于传递给MyBatis Mapper
     */
    public static Map<String, Object> buildParameterMapIgnoringNull(Object... objs) {
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < objs.length / 2; i++) {
            String key = (String) objs[i * 2];
            Object value = objs[i * 2 + 1];
            if (value != null && value instanceof String && ((String) value).isEmpty()) {
                value = null;
            }
            map.put(key, value);
        }
        return map;
    }
    
    /**
     * 构造参数Map.
     * 示例：buildParameterMap(
     *           "name1", value1, 
     *           "name2", value2, 
     *           ...,
     *           new OrderItem("field1", OrderItem.ASC),
     *           new OrderItem("field2", OrderItem.DESC)
     *       ) 
     * @param objs
     * @return
     */
    public static Map<String, Object> params(Object...objs) {
        TreeMap<String, Object> map = new TreeMap<>();
        List<OrderItem> orders = new ArrayList<>();
        
        for (int i = 0; i < objs.length; i++) {
            Object cur = objs[i];
            
            // OrderItem
            if (cur instanceof OrderItem) {
                orders.add((OrderItem) cur);
                continue;
            }
            
            // Key or Value
            String key = (String) cur;
            Object val = objs[i + 1];
            map.put(key, val);

            i++;
        }
        
        if (!orders.isEmpty()) {
            map.put(OrderItem.ORDER_KEY, orders);
        }
        
        return map;
    }
}
