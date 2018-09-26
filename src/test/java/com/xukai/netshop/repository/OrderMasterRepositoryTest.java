package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 09:28
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Resource
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void findByBuyerId() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerId("12345", pageRequest);
        Assert.assertNotNull(result);
        for (OrderMaster orderMaster : result) {
            System.out.println(orderMaster);
        }
    }
}