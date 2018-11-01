package com.xukai.netshop.service;

import com.xukai.netshop.VO.PayVO;
import com.xukai.netshop.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Xukai
 * @description: 支付接口
 * @createDate: 2018/10/31 14:17
 * @modified By:
 */
public interface PayService {

    /**
     * 支付
     *
     * @param orderId
     */
    void pay(String orderId);

    /**
     * 创建支付
     *
     * @param orderDTO
     * @param request
     * @return
     */
    PayVO create(OrderDTO orderDTO, HttpServletRequest request);

    /**
     * 支付反馈
     *
     * @param notifyData
     * @return
     */
    PayVO notify(String notifyData);

    /**
     * 退款
     *
     * @param orderDTO
     * @return
     */
    PayVO refund(OrderDTO orderDTO);
}
