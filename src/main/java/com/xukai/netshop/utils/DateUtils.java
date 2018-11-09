package com.xukai.netshop.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/26 20:49
 * Modified By:
 */
public class DateUtils {

    private static SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param date Date对象
     * @return 格式化后的时间
     */
    public static String formatTime(Date date) {
        return getTimeFormat().format(date);
    }

    /**
     * 判断一个时间是否在另一个时间之前
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean before(String time1, String time2) {
        try {
            Date dateTime1 = getTimeFormat().parse(time1);
            Date dateTime2 = getTimeFormat().parse(time2);
            if (dateTime1.before(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 判断结果
     */
    public static boolean after(String time1, String time2) {
        try {
            Date dateTime1 = getTimeFormat().parse(time1);
            Date dateTime2 = getTimeFormat().parse(time2);
            if (dateTime1.after(dateTime2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算时间差值（单位为秒）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 差值
     */
    public static int minus(String time1, String time2) {
        try {
            Date datetime1 = getTimeFormat().parse(time1);
            Date datetime2 = getTimeFormat().parse(time2);
            long millisecond = datetime1.getTime() - datetime2.getTime();
            return Integer.valueOf(String.valueOf(millisecond / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取现在时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return 现在时间
     */
    public static String getNowTime() {
        return getTimeFormat().format(new Date());
    }

    /**
     * 获取n天前的时间（yyyy-MM-dd HH:mm:ss）
     *
     * @return
     */
    public static String getNDaysAgoTime(Integer n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -n);
        Date date = cal.getTime();
        return getTimeFormat().format(date);
    }

    public static void main(String[] args) {
        String yesterdayDate = getNDaysAgoTime(20);
        System.out.println(yesterdayDate);
    }
}
