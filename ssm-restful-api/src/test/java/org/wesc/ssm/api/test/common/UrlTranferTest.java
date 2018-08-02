package org.wesc.ssm.api.test.common;

import org.junit.Test;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/7/23 16:26
 */
public class UrlTranferTest {

    @Test
    public void test(){
        String contextPath = "/ssm-web-portal";
        int index = contextPath.lastIndexOf("-");
        System.out.println(contextPath.substring(0, index) + "-api");
    }
}
