package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.service.ProductService;
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
 * @createDate: 2018/9/27 14:12
 * @modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findByCategory() {
        String categoryType = "1";
        PageRequest pageRequest = new PageRequest(1 - 1, 2);
        Page<ProductInfo> productInfos = productService.findByCategory(categoryType, pageRequest);
        for (ProductInfo productInfo : productInfos) {
            log.info(productInfo.toString());
        }
    }


}