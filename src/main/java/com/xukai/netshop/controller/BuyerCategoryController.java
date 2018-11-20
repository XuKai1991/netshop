package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.dataobject.ProductCategory;
import com.xukai.netshop.service.CategoryService;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Xukai
 * @description: 买家商品类别controller
 * @createDate: 2018/9/27 13:51
 * @modified By:
 */
@RestController
@RequestMapping("/buyer/category")
@Slf4j
public class BuyerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 买家端列出所有商品类别
     *
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<ProductCategory>> list(String shopId) {
        List<ProductCategory> categoryList = categoryService.findByShopId(shopId);
        if (categoryList == null || categoryList.size() == 0) {
            return ResultVOUtil.error(0, "fail");
        }
        return ResultVOUtil.success(categoryList);
    }
}
