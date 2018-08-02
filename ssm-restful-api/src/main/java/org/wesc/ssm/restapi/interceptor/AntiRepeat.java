package org.wesc.ssm.restapi.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防重复提交注解：在需要的Controller上加上注解@AntiRepeat.
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/24 18:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiRepeat {
}
