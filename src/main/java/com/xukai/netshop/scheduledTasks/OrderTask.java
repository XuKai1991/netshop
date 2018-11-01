package com.xukai.netshop.scheduledTasks;

import com.xukai.netshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

    /**
     * 买家十天不收货，卖家自动收货
     * 每十分钟执行一次
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void confirmReceive() {

    }

    /**
     * 买家2小时不支付自动取消订单
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelNotPayOrder() {

    }
}
