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
     * 购物车内物品ID
     */
    private String itemId;

    /**
     * 商品Id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图
     */
    private String productImgMd;

    /**
     * 商品颜色
     */
    private String productColor;

    /**
     * 商品尺码
     */
    private String productSize;

    /**
     * 状态, 0正常/1下架
     */
    private Integer productStatus;

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

    public CartDTO(String productId, String productName, String productImgMd, String productColor, String productSize, BigDecimal productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImgMd = productImgMd;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productPrice = productPrice;
    }
}
