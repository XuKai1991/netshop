package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/30 01:19
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void sellerDelete() {
        orderService.sellerDelete("1538193477003848674");
    }

    @Test
    public void findOnCondition() {
        OrderMaster s_order = new OrderMaster();
        s_order.setOrderStatus(OrderStatusEnum.HAS_SEND.getCode());
        Page<OrderDTO> orderDTOPage = orderService.findOnCondition(s_order, null, null, null);
        for (OrderDTO orderDTO : orderDTOPage) {
            log.info(orderDTO.toString());
        }

    }
}