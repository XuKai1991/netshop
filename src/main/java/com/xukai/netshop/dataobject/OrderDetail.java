package com.xukai.netshop.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 10:17
 * Modified By:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    private String detailId;

    /**
     * 订单id.
     */
    private String orderId;

    /**
     * 商品id.
     */
    private String productId;

    /**
     * 商品名称.
     */
    private String productName;

    /**
     * 商品单价.
     */
    private BigDecimal productPrice;

    /**
     * 商品颜色
     */
    private String productColor;

    /**
     * 商品尺码
     */
    private String productSize;

    /**
     * 商品数量.
     */
    private Integer productQuantity;

    /**
     * 商品图
     */
    private String productImgMd;
}
