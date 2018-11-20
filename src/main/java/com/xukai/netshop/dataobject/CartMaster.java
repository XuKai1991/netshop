package com.xukai.netshop.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author: Xukai
 * Description: 购物车
 * CreateDate: 2018/6/21 10:15
 * Modified By:
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "cart_master")
public class CartMaster {

    /**
     * 购物车ID
     */
    @Id
    @GeneratedValue
    private Integer cartId;

    /**
     * 买家ID
     */
    private String buyerId;

    /**
     * 购物车内容
     */
    private String cartItems;

    /**
     * 店铺编号
     */
    private String shopId;

    public CartMaster(String buyerId, String shopId, String cartItems) {
        this.buyerId = buyerId;
        this.shopId = shopId;
        this.cartItems = cartItems;
    }
}
