package com.xukai.netshop.service.impl;

import com.xukai.netshop.VO.PayVO;
import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.repository.OrderMasterRepository;
import com.xukai.netshop.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Xukai
 * @description: 支付接口实现类
 * @createDate: 2018/10/31 14:18
 * @modified By:
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    public void pay(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NOT_PAY.getCode())) {
            log.error("【订单付款】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new BuyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.HAS_PAY.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单付款】更新失败, orderId={}", orderMaster.getOrderId());
            throw new BuyException(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

    @Override
    public PayVO create(OrderDTO orderDTO, HttpServletRequest request) {
        return null;
    }

    @Override
    public PayVO notify(String notifyData) {
        return null;
    }

    @Override
    public PayVO refund(OrderDTO orderDTO) {
        return null;
    }
}
