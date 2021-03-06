package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.service.ProductService;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/27 13:50
 * @modified By:
 */
@RestController
@RequestMapping("/buyer/product")
@Slf4j
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResultVO<Page<ProductInfo>> list(ProductInfo s_productInfo,
                                            BigDecimal minPrice, BigDecimal maxPrice,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "6") Integer size) {
        // 只让用户查到在售的商品
        s_productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        Page<ProductInfo> productInfoPage = productService.findOnCondition(s_productInfo, minPrice, maxPrice, new PageRequest(page - 1, size));
        return ResultVOUtil.success(productInfoPage);
    }

    /**
     * 根据商品货号获得商品详情
     *
     * @param productId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO<ProductInfo> getProductDetail(@RequestParam(value = "productId") String productId) {
        ProductInfo productInfo = productService.findOne(productId);
        return ResultVOUtil.success(productInfo);
    }

}
