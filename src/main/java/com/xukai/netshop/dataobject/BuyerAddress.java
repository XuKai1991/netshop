package com.xukai.netshop.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Xukai
 * @description: 用户常用地址
 * @createDate: 2018/10/25 01:30
 * @modified By:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "buyer_address")
public class BuyerAddress {

    /**
     * 主键ID
     */
    @Id
    private String buyerAddressId;

    /**
     * 买家ID
     */
    private String buyerId;

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

    public BuyerAddress(String buyerAddressId, String buyerId, String buyerName, String buyerPhone, String buyerAddress) {
        this.buyerAddressId = buyerAddressId;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
    }
}
