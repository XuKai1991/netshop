package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description: 快递枚举
 * CreateDate: 2018/6/22 10:33
 * Modified By:
 */
@Getter
public enum ExpressShipperEnum implements CodeEnum<String, String> {

    // 快递类型
    ZHONG_TONG("zhongtong", "中通"),
    HUI_TONG("huitongkuaidi", "百世汇通"),
    EMS("ems", "EMS"),
    YUAN_TONG("yuantong", "圆通"),
    SHEN_TONG("shentong", "申通"),
    YUNDA("yunda", "韵达");

    private String code;

    private String message;

    ExpressShipperEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
