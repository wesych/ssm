package org.wesc.ssm.utils.tool;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/8/2 15:49
 */
public class CustomDateUtils {

    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式(pattern)的sdf的Map */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern  类似于yy-MM-dd HH:mm:ss
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocal = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (threadLocal == null) {
            synchronized (lockObj) {
                threadLocal = sdfMap.get(pattern);
                if (threadLocal == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    System.out.println("put new sdf of pattern " + pattern + " to map");

                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    threadLocal = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, threadLocal);
                }
            }
        }

        return threadLocal.get();
    }

    /**
     * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }

    /**
     * 获取两个日期间的工作日天数
     * @param startDate(yyyy-MM-dd)
     * @param endDate(yyyy-MM-dd)
     * @throws ParseException
     * @return
     */
    public static int getWorkdayNum(String startDate, String endDate) throws ParseException {

        SimpleDateFormat sdf = getSdf("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (start.after(end)) {
            return 0;
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(start);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        int days = endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR) + 1;

        Date date = start;
        while(date.before(end)) {
            if (!checkWorkday(sdf.format(date))) {
                days -= 1;
            }
            date = DateUtils.addDays(date, 1);
        }

        return days;
    }

    /**
     * 检查是否是周末
     * @param date
     * @return
     * @throws ParseException
     */
    public static boolean checkWorkday(String date) throws ParseException {
        Date parsed = getSdf("yyyy-MM-dd").parse(date);
        int dow = org.apache.commons.lang3.time.DateUtils.toCalendar(parsed).get(Calendar.DAY_OF_WEEK);
        return (dow != Calendar.SATURDAY && dow != Calendar.SUNDAY);
    }

}
