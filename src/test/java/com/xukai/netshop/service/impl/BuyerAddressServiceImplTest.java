package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.BuyerAddress;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.service.BuyerAddressService;
import com.xukai.netshop.utils.EnumUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/31 01:58
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BuyerAddressServiceImplTest {

    @Autowired
    private BuyerAddressService buyerAddressService;

    @Test
    public void save() {
        // BuyerAddress buyerAddress = new BuyerAddress(KeyUtils.genUniqueKey(), "1539793445691364", "刘莹", "17702615772", "辽宁省营口市站前区红运大饭店");
        // buyerAddressService.save(buyerAddress);
        Map<String, String> map = EnumUtils.listEnum(OrderStatusEnum.class);
        log.info(map.toString());
    }

    @Test
    public void findOne() {
        BuyerAddress buyerAddress = buyerAddressService.findOne("1540922551309712");
        log.info(buyerAddress.toString());
    }

    @Test
    public void findByBuyerId() {
        List<BuyerAddress> buyerAddressList = buyerAddressService.findByBuyerId("1539793445691364");
        for (BuyerAddress buyerAddress : buyerAddressList) {
            log.info(buyerAddress.toString());
        }
    }

    @Test
    public void delete() {
        buyerAddressService.delete("111");
    }
}