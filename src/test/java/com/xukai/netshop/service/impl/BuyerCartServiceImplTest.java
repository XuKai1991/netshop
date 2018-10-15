package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.CartMaster;
import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.service.BuyerCartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/28 10:40
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BuyerCartServiceImplTest {

    @Autowired
    private BuyerCartService buyerCartService;

    @Test
    public void list() {
        CartMaster cartMaster = buyerCartService.list("2d4c634ddf1444fe9854eb2d31918f5b");
        log.info(cartMaster.toString());
    }

    @Test
    public void add() {

    }

    @Test
    public void addItem() {
        String buyerId = "2d4c634ddf1444fe9854eb2d31918f5b";
        CartDetail cartDetail = new CartDetail("1538012990846466288", "帽子", "pic", "紫色", "57", new BigDecimal(50));
        buyerCartService.addItem(cartDetail, buyerId);
    }

    @Test
    public void deleteItem() {
        String buyerId = "2d4c634ddf1444fe9854eb2d31918f5b";
        String itemId = "1538114989798413137";
        buyerCartService.deleteItem(itemId, buyerId);
    }

    @Test
    public void increaseItemNum() {
        String buyerId = "2d4c634ddf1444fe9854eb2d31918f5b";
        String itemId = "1538186325817140953";
        buyerCartService.increaseItemNum(itemId, buyerId);
    }

    @Test
    public void decreaseItemNum() {
        String buyerId = "2d4c634ddf1444fe9854eb2d31918f5b";
        String itemId = "1538114989798413137";
        buyerCartService.decreaseItemNum(itemId, buyerId);
    }
}