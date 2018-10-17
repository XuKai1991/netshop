package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/27 10:36
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BuyerServiceImplTest {

    @Autowired
    private BuyerService buyerService;

    @Test
    public void checkUsername() {
        boolean flag = buyerService.checkUsername("lisi");
        log.info(flag ? "true" : "false");
    }

    @Test
    public void register() {
        BuyerInfo buyerInfo = new BuyerInfo("lisi2", "123", "1390000", "eee@qq.com");
        buyerService.save(buyerInfo);
    }

    @Test
    public void findByUsernameAndPassword() {
        String username = "lisi";
        String password = "123456";
        BuyerInfo buyerInfo = buyerService.findByUsernameAndPassword(username, password);
        log.info(buyerInfo.toString());
    }

    @Test
    public void findOnCondition() {
        BuyerInfo s_buyer = new BuyerInfo();
        s_buyer.setBuyerId("%%");
        s_buyer.setUsername("%liu%");
        s_buyer.setPhone("%%");
        s_buyer.setEmail("%%");
        PageRequest pageRequest = new PageRequest(0, 4);
        Page<BuyerInfo> buyerInfos = buyerService.findOnCondition(s_buyer, pageRequest);
        for (BuyerInfo buyerInfo : buyerInfos) {
            log.info(buyerInfo.toString());
        }
    }
}