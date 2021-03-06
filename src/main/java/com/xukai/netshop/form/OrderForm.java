package com.xukai.netshop.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/24 20:01
 * Modified By:
 */
@Data
public class OrderForm {

    /**
     * 店铺编号
     */
    @NotEmpty(message = "店铺ID必填")
    private String shopId;

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String buyerName;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    private String buyerPhone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String buyerAddress;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

    /**
     * 订单备注
     */
    private String orderRemark;
}
