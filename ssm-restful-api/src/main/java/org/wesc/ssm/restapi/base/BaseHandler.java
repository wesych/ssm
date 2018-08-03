package org.wesc.ssm.restapi.base;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.wesc.ssm.dao.entity.User;
import org.wesc.ssm.service.exception.ServiceException;

/**
 * 所有接口服务的基类.
 */
public abstract class BaseHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);
    
    /** 当前登录的用户. */
    protected User login;
    
    /** Spring ApplicationContext. */
    protected ApplicationContext applicationContext;
    
    /** 表示Handler是否已经完成初始化. */
    private boolean isInitialized = false;
    
    /**
     * 执行接口请求.
     * @param requestData 请求数据
     * @return            返回结果
     */
    public abstract APIResponse execute(JSONObject requestData) throws ServiceException;
    
    
    /**
     * 父类执行接口请求.
     * @param data              请求数据
     * @return                  返回结果
     */
    public APIResponse executeInner(JSONObject data) throws ServiceException {
        if (!this.isInitialized) {
            LOGGER.error("Handler is not initialized.");
            throw new RuntimeException("Handler没有初始化");
        }
        return execute(data);
    }
    
    /**
     * 初始化接口Hanlder.
     * 子类覆盖此方法并进行初始化，子类中必须调用super.initialize()
     * @throws ServiceException 初始化失败
     */
    public void initialize() throws ServiceException {
        this.isInitialized = true;
    }
 
    /**
     * 获取错误代码.
     * @param serviceErrorCode ServiceException的错误代码
     * @return                 int类型错误代码
     */
    public int findErrorCode(String serviceErrorCode) {
        return StatusCode.RESPONSE_ERROR;
    }
    
    /**
     * 接口是否需要登录.
     */
    protected boolean isRequireLogin() {
        return true;
    }

    public User getLogin() {
        return login;
    }

    public void setLogin(User login) {
        this.login = login;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
