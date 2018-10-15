package com.xukai.netshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xukai.netshop.dataobject.OrderDetail;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.utils.EnumUtils;
import com.xukai.netshop.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 解决OrderMaster不包含订单详情的问题，其他属性与OrderMaster相同
 * DTO：Data Transfer Object
 */
@Data
public class OrderDTO {

    /**
     * 订单id.
     */
    private String orderId;

    /**
     * 买家id.
     */
    private String buyerId;

    /**
     * 买家名字.
     */
    private String buyerName;

    /**
     * 买家手机号.
     */
    private String buyerPhone;

    /**
     * 买家地址.
     */
    private String buyerAddress;

    /**
     * 订单应付总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单实付总金额.
     */
    private BigDecimal orderActualAmount;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 创建时间.
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间.
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtils.getMsgByCode(orderStatus, OrderStatusEnum.class);
    }

}
