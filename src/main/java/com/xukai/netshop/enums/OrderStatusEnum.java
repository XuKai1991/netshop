package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:33
 * Modified By:
 */
@Getter
public enum OrderStatusEnum implements CodeEnum<Integer>{

    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    BUYER_DEL(3, "买家删除");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
