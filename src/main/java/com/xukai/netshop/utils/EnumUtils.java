package com.xukai.netshop.utils;

import com.xukai.netshop.enums.CodeEnum;

import java.util.HashMap;
import java.util.Map;

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

    public static <T extends CodeEnum> T getMsgByCode(String code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

    public static <T extends CodeEnum> Map<String, String> listEnum(Class<T> enumClass){
        HashMap<String, String> enumMap = new HashMap<>(10);
        for (T each : enumClass.getEnumConstants()) {
            enumMap.put(each.getCode().toString(), each.getMessage().toString());
        }
        return enumMap;
    }
}
