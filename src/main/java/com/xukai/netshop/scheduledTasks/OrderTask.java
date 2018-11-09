package com.xukai.netshop.scheduledTasks;

import com.xukai.netshop.config.AutoAdminConfig;
import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.service.OrderService;
import com.xukai.netshop.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: Xukai
 * @description: 十天不收货自动确认收货
 * @createDate: 2018/10/31 21:56
 * @modified By:
 */
@Configuration
@Slf4j
public class OrderTask {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AutoAdminConfig autoAdminConfig;

    /**
     * 买家n天不收货，系统自动收货
     * 每1小时执行一次（30分钟时）
     */
    @Scheduled(cron = "0 30 */1 * * ?")
    public void confirmReceive() {
        OrderMaster sOrder = new OrderMaster();
        sOrder.setOrderStatus(OrderStatusEnum.HAS_SEND.getCode());
        Page<OrderDTO> orderDTOPage = orderService.findOnCondition(sOrder, null, null, null);
        for (OrderDTO orderDTO : orderDTOPage) {
            if (DateUtils.before(DateUtils.formatTime(orderDTO.getUpdateTime()), DateUtils.getNDaysAgoTime(autoAdminConfig.getAutoConfirmReceiveGoodsWaitTime()))) {
                orderService.receive(orderDTO.getOrderId());
            }
        }
    }

    /**
     * 买家2小时不支付自动取消订单
     * 每5分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void cancelNotPayOrder() {
        OrderMaster sOrder = new OrderMaster();
        sOrder.setOrderStatus(OrderStatusEnum.NOT_PAY.getCode());
        Page<OrderDTO> orderDTOPage = orderService.findOnCondition(sOrder, null, null, null);
        for (OrderDTO orderDTO : orderDTOPage) {
            if (DateUtils.minus(DateUtils.getNowTime(), DateUtils.formatTime(orderDTO.getUpdateTime())) > autoAdminConfig.getAutoCancelNotPayedOrderWaitTime()) {
                orderService.cancel(orderDTO.getOrderId());
            }
        }
    }
}
