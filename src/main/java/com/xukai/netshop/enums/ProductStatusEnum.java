package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description: 商品状态
 * CreateDate: 2018/6/21 14:14
 * Modified By:
 */
@Getter
public enum ProductStatusEnum implements CodeEnum<Integer, String> {

    // 商品状态
    UP(0, "在售"),
    Down(1, "停售");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
