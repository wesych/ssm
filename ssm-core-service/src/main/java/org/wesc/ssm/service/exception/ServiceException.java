package org.wesc.ssm.service.exception;

/**
 * @Description: 服务异常
 * @Auther: Wesley Cheung
 * @Date: 2018/7/11 14:34
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    /** 错误代码. */
    private String errorCode;

    /** 其它异常信息. */
    private Object data;

    public ServiceException(String errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
