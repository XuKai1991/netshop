package com.xukai.netshop.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.utils.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Xukai
 * Description: 订单详情
 * CreateDate: 2018/6/21 10:15
 * Modified By:
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "order_master")
public class OrderMaster {

    /**
     * 订单id
     */
    @Id
    private String orderId;

    /**
     * 买家id
     */
    private String buyerId;

    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 订单应付总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单实付总金额
     */
    private BigDecimal orderActualAmount;

    /**
     * 订单状态, 默认为0
     * 0:新下单
     * 1:完成
     * 2:取消
     * 3:买家删除
     */
    private String orderStatus;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtils.getMsgByCode(orderStatus, OrderStatusEnum.class);
    }
}
