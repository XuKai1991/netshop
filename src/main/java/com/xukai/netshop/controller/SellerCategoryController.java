package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.dataobject.ProductCategory;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.form.CategoryForm;
import com.xukai.netshop.service.CategoryService;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.xukai.netshop.constant.CrawlerCons.RANDOM;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/9 11:07
 * @modified By:
 */
@RestController
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list() {
        List<ProductCategory> categoryList = categoryService.findAll();
        ModelAndView mav = new ModelAndView("sell/category/list");
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    @GetMapping("/detail")
    public ResultVO detail(@RequestParam(value = "categoryId") Integer categoryId) {
        ProductCategory category = categoryService.findOne(categoryId);
        return ResultVOUtil.success(category);
    }

    @PostMapping("/save")
    public ResultVO save(@Valid CategoryForm categoryForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        ProductCategory category;
        if (categoryForm.getCategoryId() != null) {
            category = categoryService.findOne(categoryForm.getCategoryId());
            category.setCategoryName(categoryForm.getCategoryName());
        } else {
            category = new ProductCategory();
            int categoryType;
            do {
                categoryType = RANDOM.nextInt(1000);
            } while (categoryService.checkCategoryTypeExist(String.valueOf(categoryType)));
            category.setCategoryType(String.valueOf(categoryType));
            category.setCategoryName(categoryForm.getCategoryName());
        }
        categoryService.save(category);
        return ResultVOUtil.success();
    }

    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("categoryId") Integer categoryId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/seller/category/list");
        try {
            categoryService.deleteByCategoryId(categoryId);
        } catch (SellException e) {
            log.error("【卖家端删除商品类目】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.CATEGORY_DELETE_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }
}
