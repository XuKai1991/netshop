package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.ExpressInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/11/6 10:45
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExpressInfoRepositoryTest {

    @Autowired
    private ExpressInfoRepository expressInfoRepository;

    @Test
    public void deleteByOrderId() {
        String orderId = "111";
        int del = expressInfoRepository.deleteByOrderId(orderId);
        System.out.println(del);
    }

    @Test
    public void findByExpressStatus() {
        List<ExpressInfo> byExpressStatus = expressInfoRepository.findByExpressStatus(0);
        for (ExpressInfo expressStatus : byExpressStatus) {
            log.info(expressStatus.toString());
        }
    }
}