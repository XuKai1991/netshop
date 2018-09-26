package com.xukai.netshop.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 13:48
 * Modified By:
 */
public class KeyUtils {

    /**
     * 获取UUID
     * @return
     */
    public static synchronized String genUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static void main(String[] args) {
        System.out.println(KeyUtils.genUUID());
        System.out.println(KeyUtils.genUniqueKey());
    }
}
