package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:33
 * Modified By:
 */
@Getter
public enum OrderStatusEnum implements CodeEnum<String, String> {

    /**
     * 订单状态, 默认为0
     * 0:未支付
     * 1:已取消
     * 2:已支付
     * 3:退款中
     * 4:已发货
     * 5:已收货
     * 6:买家删除
     */
    NOT_PAY("0", "未支付"),
    CANCEL("1", "已取消"),
    HAS_PAY("2", "已支付"),
    IN_REFUND("3", "退款中"),
    HAS_SEND("4", "已发货"),
    HAS_RECEIVE("5", "已收货"),
    BUYER_DEL("6", "买家删除");

    private String code;

    private String message;

    OrderStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
