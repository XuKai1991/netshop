package com.xukai.netshop.utils;

import com.xukai.netshop.enums.CodeEnum;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/28 13:16
 * Modified By:
 */
public class EnumUtils {

    public static <T extends CodeEnum> T getMsgByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
