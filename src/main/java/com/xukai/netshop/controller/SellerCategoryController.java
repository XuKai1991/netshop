package com.xukai.netshop.controller;

import com.xukai.netshop.dataobject.ProductCategory;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.form.CategoryForm;
import com.xukai.netshop.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/9 11:07
 * @modified By:
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list() {
        List<ProductCategory> categoryList = categoryService.findAll();
        ModelAndView mav = new ModelAndView("category/list");
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        ModelAndView mav = new ModelAndView();
        ProductCategory category;
        if (categoryId != null) {
            try {
                category = categoryService.findOne(categoryId);
            } catch (SellException e) {
                log.error("【卖家端查询商品类目】发生异常{}", e);
                mav.addObject("msg", e.getMessage());
                mav.addObject("url", "/netshop/seller/category/list");
                mav.setViewName("common/error");
                return mav;
            }
            mav.addObject("category", category);
        }
        mav.setViewName("category/index");
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.addObject("msg", bindingResult.getFieldError().getDefaultMessage());
            mav.addObject("url", "/netshop/seller/category/index");
            mav.setViewName("common/error");
            return mav;
        }
        ProductCategory category = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null) {
                category = categoryService.findOne(categoryForm.getCategoryId());
            }
        } catch (SellException e) {
            log.error("【卖家端保存商品类目】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/netshop/seller/category/index?categoryId=" + categoryForm.getCategoryId());
            mav.setViewName("common/error");
            return mav;
        }
        BeanUtils.copyProperties(categoryForm, category);
        categoryService.save(category);
        mav.addObject("url", "/netshop/seller/category/list");
        mav.setViewName("common/success");
        return mav;
    }
}
