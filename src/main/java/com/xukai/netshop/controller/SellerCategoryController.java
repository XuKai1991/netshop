package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.ProductCategory;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.form.CategoryForm;
import com.xukai.netshop.service.CategoryService;
import com.xukai.netshop.utils.ResultVOUtil;
import com.xukai.netshop.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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

    @Autowired
    private CookieConfig cookieConfig;

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        String shopId = TokenUtils.getToken(cookieConfig.getShopId(), request);
        // 如果默认分类不存在则添加
        if (!categoryService.checkCategoryTypeExist("-1", shopId)) {
            categoryService.save(new ProductCategory("未分类", "-1", shopId));
        }
        List<ProductCategory> categoryList = categoryService.findByShopId(shopId);
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
    public ResultVO save(@Valid CategoryForm categoryForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        String shopId = TokenUtils.getToken(cookieConfig.getShopId(), request);
        ProductCategory category;
        if (categoryForm.getCategoryId() != null) {
            category = categoryService.findOne(categoryForm.getCategoryId());
            category.setCategoryName(categoryForm.getCategoryName());
        } else {
            category = new ProductCategory();
            int categoryType;
            do {
                categoryType = RANDOM.nextInt(1000);
            } while (categoryService.checkCategoryTypeExist(String.valueOf(categoryType), shopId));
            category.setCategoryType(String.valueOf(categoryType));
            category.setShopId(shopId);
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
