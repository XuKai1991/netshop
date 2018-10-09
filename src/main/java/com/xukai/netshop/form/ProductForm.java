package com.xukai.netshop.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/29 16:41
 * Modified By:
 */
@Data
public class ProductForm {

    private String productId;

    /**
     * 名字.
     */
    private String productName;

    /**
     * 单价.
     */
    private BigDecimal productPrice;

    /**
     * 进价
     */
    private BigDecimal productPurchasePrice;

    /**
     * 商品颜色
     */
    private String productColor;

    /**
     * 商品尺码
     */
    private String productSize;

    /**
     * 库存.
     */
    private Integer productStock;

    /**
     * 描述.
     */
    private String productDescription;

    /**
     * 主图
     */
    private String productImgMd;

    /**
     * 详情展示图
     */
    private String productDetailImg;

    /**
     * 类目编号.
     */
    private Integer categoryType;
}
