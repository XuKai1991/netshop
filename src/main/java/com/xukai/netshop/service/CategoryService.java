package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.ProductCategory;

import java.util.List;

/**
 * Author: Xukai
 * Description: 类目接口
 * CreateDate: 2018/6/21 13:39
 * Modified By:
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    ProductCategory save(ProductCategory productCategory);

    void deleteByCategoryId(Integer categoryId);
}
