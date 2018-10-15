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
}
