package com.xukai.netshop.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 14:14
 * Modified By:
 */
@Data
public class CartDTO {

    /**
     * 商品Id.
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品小图
     */
    private String productIcon;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 数量.
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

}
