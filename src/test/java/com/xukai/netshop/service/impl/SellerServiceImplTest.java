package com.xukai.netshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.service.SellerService;
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
        // String username = "xukai";
        // String password = "123";
        // SellerInfo sellerInfo = sellerService.findByUsernameAndPassword(username, password);
        // log.info(sellerInfo.toString());

        String items = "[{\"itemId\":\"1538111515379311996\",\"productColor\":\"黑色\",\"productId\":\"1538012990846466286\",\"productImgMd\":\"pic\",\"productName\":\"衣服\",\"productPrice\":200,\"productQuantity\":2,\"productSize\":\"38\"}]";
        List<CartDetail> orderDetailList = JSONObject.parseArray(items, CartDetail.class);
        for (CartDetail orderDetail : orderDetailList) {
            log.info(orderDetail.toString());
        }
    }
}