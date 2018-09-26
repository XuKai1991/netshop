package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:38
 * Modified By:
 */
@Getter
public enum PayStatusEnum implements CodeEnum<Integer> {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功");

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
