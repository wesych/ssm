package org.wesc.ssm.portal.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Log记录访问url.
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/20 16:16
 */
public class LogUriInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(LogUriInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        StringBuilder sb = new StringBuilder();

        String uri = request.getRequestURI();
        sb.append("URI Visited: ").append(uri);

        Enumeration<String> enums = request.getParameterNames();
        if (enums.hasMoreElements()) {
            sb.append(" - ");
        }
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            sb.append("\"").append(key).append("\":").append(
                    request.getParameter(key)).append(", ");
        }
        logger.info(sb.toString());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
    }

}
