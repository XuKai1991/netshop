package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description: 商品状态
 * CreateDate: 2018/6/21 14:14
 * Modified By:
 */
@Getter
public enum ExpressStatusEnum implements CodeEnum<Integer, String> {

    IN_TRANSIT(0, "运输中"),
    HAS_RECEIVED(1, "已收货");

    private Integer code;

    private String message;

    ExpressStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
