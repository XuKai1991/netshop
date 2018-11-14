package com.xukai.netshop.controller;

import com.xukai.netshop.dataobject.ProductCategory;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.form.ProductForm;
import com.xukai.netshop.service.CategoryService;
import com.xukai.netshop.service.ProductService;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/29 13:53
 * Modified By:
 */
@RestController
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(ProductInfo s_productInfo, BigDecimal minPrice, BigDecimal maxPrice,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "7") Integer size) {
        ModelAndView mav = new ModelAndView();
        try {
            Page<ProductInfo> productInfoPage = productService.findOnCondition(s_productInfo, minPrice, maxPrice, new PageRequest(page - 1, size));
            List<ProductCategory> categoryList = categoryService.findAll();
            mav.addObject("categoryList", categoryList);
            mav.addObject("productInfoPage", productInfoPage);
        } catch (SellException e) {
            log.error("【卖家端商品下架】发生异常{}", e);
            mav.addObject("url", "/seller/product/list");
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.setViewName("sell/product/list");
        mav.addObject("s_productInfo", s_productInfo);
        mav.addObject("minPrice", minPrice);
        mav.addObject("maxPrice", maxPrice);
        mav.addObject("currentPage", page);
        mav.addObject("size", size);
        return mav;
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/seller/product/list");
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            log.error("【卖家端商品下架】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.PRODUCT_OFF_SALE_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/seller/product/list");
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            log.error("【卖家端商品上架】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.PRODUCT_ON_SALE_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId) {
        ModelAndView mav = new ModelAndView();
        ProductInfo productInfo;
        if (!StringUtils.isEmpty(productId)) {
            try {
                productInfo = productService.findOne(productId);
            } catch (SellException e) {
                log.error("【卖家端查询商品详情】发生异常{}", e);
                mav.addObject("msg", e.getMessage());
                mav.addObject("url", "/seller/product/list");
                mav.setViewName("sell/common/error");
                return mav;
            }
            mav.addObject("productInfo", productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        mav.addObject("categoryList", categoryList);
        mav.setViewName("sell/product/index");
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject("msg", bindingResult.getFieldError().getDefaultMessage());
            mav.addObject("url", "/seller/product/index");
            mav.setViewName("sell/common/error");
            return mav;
        }
        ProductInfo productInfo;
        try {
            if (StringUtils.isEmpty(productForm.getProductId())) {
                productInfo = new ProductInfo();
                productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
                productForm.setProductId(KeyUtils.genUniqueKey());
            } else {
                productInfo = productService.findOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            log.error("【卖家端保存商品详情】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/product/index?productId=" + productForm.getProductId());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("url", "/seller/product/list");
        mav.setViewName("sell/common/success");
        return mav;
    }
}
