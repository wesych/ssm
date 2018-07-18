package org.wesc.ssm.portal.controller;

import java.io.Serializable;

/**
 * @author Wesley Cheung
 * @Date Created in 14:39 2017/12/20
 */
public class AjaxResponse implements Serializable {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5687785804754255609L;

    /**
     * 返回结果代码：成功.
     */
    public static final int RESULT_SUCCESS = 1;

    /**
     * 返回结果代码：失败.
     */
    public static final int RESULT_ERROR = 0;

    /**
     * 返回结果代码.
     */
    private int result;

    /**
     * 错误消息.
     */
    private String message;

    /**
     * 发生错误的字段.
     */
    private String field;

    /**
     * 返回数据.
     */
    private Object data;

    public AjaxResponse() {
    }

    public AjaxResponse(int result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public AjaxResponse(int result, String message, String field, Object data) {
        this.result = result;
        this.message = message;
        this.field = field;
        this.data = data;
    }

    public static AjaxResponse createSuccessResponse() {
        return new AjaxResponse(RESULT_SUCCESS, null, null);
    }

    public static AjaxResponse createSuccessResponse(Object data) {
        return new AjaxResponse(RESULT_SUCCESS, null, data);
    }

    public static AjaxResponse createFailResponse(String message) {
        return new AjaxResponse(RESULT_ERROR, message, null);
    }

    public static AjaxResponse createFailResponse(String message, Object data) {
        return new AjaxResponse(RESULT_ERROR, message, data);
    }

    public static AjaxResponse createFailResponse(String message, String field, Object data) {
        return new AjaxResponse(RESULT_ERROR, message, field, data);
    }

    public static AjaxResponse createFailResponse(String message, String field) {
        return new AjaxResponse(RESULT_ERROR, message, field);
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
