package com.xukai.netshop.dataobject.mapper;

import com.xukai.netshop.dataobject.BuyerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 21:45
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BuyerMapperTest {

    @Autowired
    private BuyerMapper buyerMapper;

    @Test
    public void register() {
    }

    @Test
    public void findByUsernameAndPassword() {
        BuyerInfo buyerInfo = buyerMapper.findByUsernameAndPassword("zhangsan", "123");
        log.info(buyerInfo.toString());
    }
}