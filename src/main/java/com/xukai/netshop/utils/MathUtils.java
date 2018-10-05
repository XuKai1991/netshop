package com.xukai.netshop.utils;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/27 16:58
 * Modified By:
 */
public class MathUtils {

    /**
     * 比较金额是否相等
     */
    public static Boolean equals(Double d1, Double d2, Double moneyDiffScale) {
        Double result = Math.abs(d1 - d2);
        return result < moneyDiffScale;
    }
}
