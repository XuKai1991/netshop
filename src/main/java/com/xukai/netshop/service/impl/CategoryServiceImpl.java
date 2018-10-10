package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.ProductCategory;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.repository.ProductCategoryRepository;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Xukai
 * Description: 类目接口实现类
 * CreateDate: 2018/6/21 13:42
 * Modified By:
 */
@Service
@CacheConfig(cacheNames = "category")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    private static final String CATEGORY_CACHE_NAME = "productCategories";

    @Override
    @Cacheable(value = CATEGORY_CACHE_NAME, key = "#categoryId")
    public ProductCategory findOne(Integer categoryId) {
        ProductCategory productCategory = productCategoryRepository.findOne(categoryId);
        if (productCategory == null) {
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        return productCategory;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    @CachePut(value = CATEGORY_CACHE_NAME, key = "#productCategory.categoryId")
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    @CacheEvict(value = CATEGORY_CACHE_NAME, key = "#categoryId")
    @Transactional(rollbackFor = SellException.class)
    public void deleteByCategoryId(Integer categoryId) {
        // 删除类目前需要对该类目的商品做处理
        // 下架商品，并修改商品类目为默认
        Integer categoryType = findOne(categoryId).getCategoryType();
        List<ProductInfo> productInfos = productInfoRepository.findByCategoryType(categoryType);
        for (ProductInfo productInfo : productInfos) {
            if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
                productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
            }
            productInfo.setCategoryType(0);
            productInfoRepository.save(productInfo);
        }
        try {
            productCategoryRepository.deleteByCategoryId(categoryId);
        } catch (Exception e) {
            throw new SellException(ResultEnum.CATEGORY_DELETE_FAIL);
        }
    }
}
