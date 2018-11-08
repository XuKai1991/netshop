package com.xukai.netshop.dataobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xukai.netshop.utils.serializer.ExpressShipperCodeSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Xukai
 * @description: 快递信息
 * @createDate: 2018/11/5 21:15
 * @modified By:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "express_info")
public class ExpressInfo {

    /**
     * 订单号
     */
    @Id
    private String orderId;

    /**
     * 快递状态：0运输中/1已收货
     */
    private Integer expressStatus;

    /**
     * 快递公司：圆通、申通 …
     */
    @JsonSerialize(using = ExpressShipperCodeSerializer.class)
    private String expressShipper;

    /**
     * 快递单号
     */
    private String expressNumber;

    /**
     * 物流详情
     */
    private String logisticsDetail;

}
