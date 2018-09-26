package com.xukai.netshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/26 20:49
 * Modified By:
 */
public class DateUtils {

    /**
     * 日期转为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            result = sdf.format(date);
        }
        return result;
    }


    /**
     * 字符串转为日期
     *
     * @param str
     * @param format
     * @return
     * @throws Exception
     */
    public static Date formatString(String str, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }
}
