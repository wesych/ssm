package org.wesc.ssm.utils.tool;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: Wesley Cheung
 * @Date: 2018/8/2 15:49
 */
public class DateUtil {

    /**
     * 获取两个日期间的工作日天数
     * @param startDate(yyyy-MM-dd)
     * @param endDate(yyyy-MM-dd)
     * @throws ParseException
     * @return
     */
    public static int getWorkdayNum(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        int dow = DateUtils.toCalendar(parsed).get(Calendar.DAY_OF_WEEK);
        return (dow != Calendar.SATURDAY && dow != Calendar.SUNDAY);
    }

}
