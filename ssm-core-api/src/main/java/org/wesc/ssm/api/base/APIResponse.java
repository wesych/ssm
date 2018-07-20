package org.wesc.ssm.api.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 接口返回格式（JSON）
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/19 18:54
 */
public class APIResponse {

    /** 返回代码：成功.*/
    public static final int RESPONSE_SUCCESS = 0;

    /** 返回代码：失败.*/
    public static final int RESPONSE_ERROR = 1;

    /** 返回代码：需要登录（失败）. */
    public static final int RESPONSE_REQUIRE_LOGIN = 9;


    /** 返回代码，0为成功，其他为失败. */
    private int result;

    /** 返回消息. */
    private String message;

    /** 其他数据 */
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将当前接口返回数据转为JSON字符串.
     * @return JSON字符串
     */
    public String toJSONString() {
        JSONObject obj = (JSONObject) JSON.toJSON(this);
        return obj.toJSONString();
    }

    /**
     * 创建接口调用成功的返回格式
     * @param data
     * @return
     */
    public static APIResponse createSuccessResponse(Object data) {
        APIResponse resp = new APIResponse();
        resp.setResult(RESPONSE_SUCCESS);
        resp.setMessage("Success");
        resp.setData(data);
        return resp;
    }

    /**
     * 调用接口失败(其他原因)
     * @param message
     * @return
     */
    public static APIResponse createFailResponse(String message) {
        APIResponse resp = new APIResponse();
        resp.setResult(RESPONSE_ERROR);
        resp.setMessage(message);
        return resp;
    }

    /**
     * 调用接口失败：需要登录
     * @param message
     * @return
     */
    public static APIResponse createFailLoginResponse(String message) {
        APIResponse resp = new APIResponse();
        resp.setResult(RESPONSE_REQUIRE_LOGIN);
        resp.setMessage(message);
        return resp;
    }

}
