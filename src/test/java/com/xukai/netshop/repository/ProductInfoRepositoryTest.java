package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
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
 * @createDate: 2018/9/27 22:18
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatusOrderByCreateTimeDesc() {
        Page<ProductInfo> productInfos = productInfoRepository.findByProductStatusOrderByCreateTimeDesc(ProductStatusEnum.UP.getCode(), new PageRequest(0, 2));
        for (ProductInfo productInfo : productInfos) {
            log.info(productInfo.toString());
        }
    }
}