package org.wesc.ssm.restapi.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 用于处理Handler和Method的对应关系.
 */
public class HandlerMapper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerMapper.class);

    /**
     * 保存所有Handler信息.
     */
    private static Map<String, HandlerInfo> handlers = new HashMap<>();
    
    /**
     * 初始化method和handler对应关系
     */
    static {
        scanHandlers();
    }

    @SuppressWarnings("unchecked")
    private static void scanHandlers() {
        LOGGER.info("Staring to scan interface handlers.");

        String basePackage = HandlerMapper.class.getPackage().getName() + ".handlers"; 
        
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                String superClassName = metadataReader.getClassMetadata().getSuperClassName();
                return BaseHandler.class.getName().equals(superClassName);
            }
        });
        Set<BeanDefinition> beans = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition bean : beans) {
            String fullName = bean.getBeanClassName();
            try {
                Class<? extends BaseHandler> clazz = (Class<? extends BaseHandler>) Class.forName(fullName);
                String methodName = findHandlerMethodName(fullName, basePackage);
                register(methodName, clazz);
                
            } catch (ClassNotFoundException e) {
                LOGGER.error("Failed to auto register handler: " + fullName, e);
            }
        }
    }
    
    /**
     * 注册一个Handler.
     * @param method       接口名
     * @param handlerClass Handler类
     */
    private static void register(String method, Class<? extends BaseHandler> handlerClass) {
        LOGGER.info("Registered handler: {} -> {}", method, handlerClass.getSimpleName());
        HandlerInfo hi = new HandlerInfo();
        hi.handlerClass = handlerClass;
        handlers.put(method, hi);
    }
     
    /**
     * 根据接口名获取Handler类.
     * @param method 接口名
     * @return       Handler类
     */
    public static Class<? extends BaseHandler> findHandlerClassByMethod(String method) {
        HandlerInfo hi = handlers.get(method);
        if (hi == null) {
            return null;
        }
        return hi.handlerClass;
    }
    
    /**
     * 根据接口类获取接口名. (仅在单元测试使用)
     * @param clazz 接口类
     * @return      接口名
     */
    public static String findMethodByHandlerClass(Class<? extends BaseHandler> clazz) {
        for (Entry<String, HandlerInfo> e : handlers.entrySet()) {
            if (e.getValue().handlerClass.equals(clazz)) {
                return e.getKey();
            }
        }
        return null;
    }
    
    /**
     * 获取所有method.
     * @return 所有method
     */
    public static List<String> listAllHandlerMethods() {
        List<String> list = new ArrayList<>();
        list.addAll(handlers.keySet());
        Collections.sort(list);
        return list;
    }
    
    /**
     * 将完整类名转换为接口的method名称.
     * @param handlerClassName Handler的完整类名 
     * @param basePackage      Hanlder最上层包路径
     * @return                 接口method名
     */
    private static String findHandlerMethodName(String handlerClassName, String basePackage) {
        String part = handlerClassName.substring(basePackage.length() + 1);
        String[] parts = part.split("\\.");
        ArrayList<String> pathParts = new ArrayList<>();
        for (int i = 0; i < parts.length - 1; i++) {
            pathParts.add(parts[i]);
        }
        
        String className = parts[parts.length - 1];
        if (className.endsWith("Handler")) {
            className = className.substring(0, className.length() - "Handler".length());
        }
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        pathParts.add(className);
        
        return StringUtils.join(pathParts, "/");
    }
    
    /**
     * 表示记录的一个Hanlder的信息.
     */
    public static class HandlerInfo {

        /**
         * Handler类.
         */
        Class<? extends BaseHandler> handlerClass;
        
    }
}
