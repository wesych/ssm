package org.wesc.ssm.restapi.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个用户 相同url 同时提交 相同数据 验证
 * 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/24 18:01
 */
public class RepeatDataInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AntiRepeat annotation = method.getAnnotation(AntiRepeat.class);
            if (annotation != null) {
                if (repeatDataValidator(request)) {
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证同一个url数据是否相同提交,相同返回true
     *
     * @param request
     * @return
     */
    public boolean repeatDataValidator(HttpServletRequest request) {
        String params = JSON.toJSONString(request.getParameterMap());
        String url = request.getRequestURI();
        Map<String, String> map = new HashMap<>();
        map.put(url, params);
        String nowUrlParams = map.toString();//

        Object preUrlParams = request.getSession().getAttribute("repeatData");
        //如果上一个数据为null,表示还没有访问页面
        if (preUrlParams == null) {
            request.getSession().setAttribute("repeatData", nowUrlParams);
            return false;
        } else {
            //如果上次url+数据和本次url+数据相同，则表示重复添加数据
            if (preUrlParams.toString().equals(nowUrlParams)) {
                return true;
            } else {
                request.getSession().setAttribute("repeatData", nowUrlParams);
                return false;
            }
        }
    }

}

