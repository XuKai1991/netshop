package com.xukai.netshop.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
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
     * 买家id.
     */
    @Id
    private String buyerId;

    /**
     * 购物车内容
     */
    private String cartItems;

    public CartMaster(String buyerId, String cartItems) {
        this.buyerId = buyerId;
        this.cartItems = cartItems;
    }
}
