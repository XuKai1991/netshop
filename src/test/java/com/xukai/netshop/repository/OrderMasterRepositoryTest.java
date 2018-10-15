package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 09:28
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterRepositoryTest {

    @Resource
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void findByBuyerId() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerIdOrderByCreateTimeDesc("12345", pageRequest);
        Assert.assertNotNull(result);
        for (OrderMaster orderMaster : result) {
            System.out.println(orderMaster);
        }
    }

    @Test
    public void findAllOrderByCreateTime() {
        PageRequest pageRequest = new PageRequest(0, 5);
        Page<OrderMaster> result = orderMasterRepository.findByCreateTimeBeforeOrderByCreateTimeDesc(new Date(), pageRequest);
        Assert.assertNotNull(result);
        for (OrderMaster orderMaster : result) {
            System.out.println(orderMaster);
        }
    }

    @Test
    public void findByOrderIdLikeAndBuyerNameLikeAndBuyerAddressLikeAndBuyerPhoneLikeAndOrderAmountBetweenOrderByCreateTimeDesc() {
        String orderStatus = "%%";
        String orderId = "%%";
        String buyerId = "%%";
        String buyerName = "%六%";
        String buyerAddress = "%%";
        String buyerPhone = "%%";
        BigDecimal minAmount = new BigDecimal("200");
        BigDecimal maxAmount = new BigDecimal("500");
        PageRequest pageRequest = new PageRequest(0, 4);
        Page<OrderMaster> orderMasters = orderMasterRepository.findByOrderStatusLikeAndOrderIdLikeAndBuyerIdLikeAndBuyerNameLikeAndBuyerAddressLikeAndBuyerPhoneLikeAndOrderAmountBetweenOrderByCreateTimeDesc(
                orderStatus, orderId, buyerId, buyerName, buyerAddress, buyerPhone, minAmount, maxAmount, pageRequest
        );
        for (OrderMaster orderMaster : orderMasters) {
            log.info("==========================");
            log.info(orderMaster.toString());
        }
    }
}