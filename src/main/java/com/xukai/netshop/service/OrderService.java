package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 11:01
 * Modified By:
 */
public interface OrderService {

    /**
     * 创建订单.
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单.
     */
    OrderDTO findOne(String orderId);

    /**
     * 根据条件搜索订单
     * @param s_order
     * @param minAmount
     * @param maxAmount
     * @param pageable
     * @return
     */
    Page<OrderDTO> findOnCondition(OrderMaster s_order, BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);

    /**
     * 取消订单.
     */
    void cancel(String orderId);

    /**
     * 订单发货.
     */
    void send(String orderId);

    /**
     * 订单收货.
     */
    void receive(String orderId);

    /**
     * 买家删除订单
     *
     * @param orderId
     */
    void buyerDelete(String orderId);

    /**
     * 买家删除订单
     *
     * @param orderId
     */
    void sellerDelete(String orderId);

    /**
     * 查找并验证订单
     *
     * @param orderId
     * @param openid
     */
    void findAndCheckOrderOne(String orderId, String openid);

    /**
     * 修改订单实付金额
     *
     * @param orderId
     * @param amount
     * @param actualAmount
     */
    void editActualAmount(String orderId, String amount, String actualAmount);

}
