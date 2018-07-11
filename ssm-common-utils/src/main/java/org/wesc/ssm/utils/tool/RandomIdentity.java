package org.wesc.ssm.utils.tool;

import java.util.Random;

/**
 * @author Wesley Cheung
 * @Date Created in 18:36 2017/12/28
 */
public class RandomIdentity {

    /**
     * 根据指定长度生成字母和数字的随机数
     *
     * @param length
     */
    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//随机用以下三个随机生成器
        Random randomData = new Random();
        int data;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randomData.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randomData.nextInt(26) + 65;//保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = randomData.nextInt(26) + 97;//保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 根据指定长度生成纯数字的随机数
     *
     * @param length
     */
    public static String createRandomNumeric(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}
