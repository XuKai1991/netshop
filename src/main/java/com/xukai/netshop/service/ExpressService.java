package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.ExpressInfo;

import java.util.List;

/**
 * @author: Xukai
 * @description: 物流服务接口
 * @createDate: 2018/10/25 01:44
 * @modified By:
 */
public interface ExpressService {

    /**
     * 保存快递信息
     *
     * @param expressInfo
     * @return
     */
    void save(ExpressInfo expressInfo);

    /**
     * 根据订单号删除
     *
     * @param orderId
     * @return
     */
    void deleteByOrderId(String orderId);

    /**
     * 确认收货
     *
     * @param OrderId
     */
    void receive(String OrderId);

    /**
     * 根据订单号查找
     *
     * @param orderId
     * @return
     */
    ExpressInfo findByOrderId(String orderId);

    /**
     * 查找所有运输中的订单
     *
     * @return
     */
    List<ExpressInfo> listInTransit();
}
