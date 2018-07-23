package org.wesc.ssm.api.base;

/**
 * 服务器端返回状态码
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/7/23 15:44
 */
public interface StatusCode {

    /** 请求成功 */
    int RESPONSE_SUCCESS = 200;

    /** 请求失败 */
    int RESPONSE_ERROR = 401;

    /** 请求失败，需要登录 */
    int RESPONSE_ERROR_REQUIRE_LOGIN  = 500;

}
