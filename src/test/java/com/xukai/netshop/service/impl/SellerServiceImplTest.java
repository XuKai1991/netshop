package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.SellerInfo;
import com.xukai.netshop.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/25 23:29
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        String username = "xukai";
        String password = "123";
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(username, password);
        log.info(sellerInfo.toString());
    }
}