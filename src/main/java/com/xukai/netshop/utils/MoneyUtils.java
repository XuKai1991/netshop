package com.xukai.netshop.utils;

import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/26 16:40
 * Modified By:
 */
public class MoneyUtils {

    /**
     * 元转分
     */
    public static Integer yuanToFen(BigDecimal yuan) {
        return yuan.movePointRight(2).intValue();
    }

    /**
     * 分转元
     */
    public static Double fenToYuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2).doubleValue();
    }

}
