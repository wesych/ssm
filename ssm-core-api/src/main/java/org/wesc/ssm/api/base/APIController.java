package org.wesc.ssm.api.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/7/19 19:33
 */
@Controller
@RequestMapping("/api")
public class APIController implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

}
