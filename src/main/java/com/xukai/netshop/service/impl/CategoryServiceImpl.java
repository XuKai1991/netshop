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

import static com.xukai.netshop.constant.CacheCons.PRODUCT_CATEGORY_CACHE_NAME;

/**
 * Author: Xukai
 * Description: 类目接口实现类
 * CreateDate: 2018/6/21 13:42
 * Modified By:
 */
@Service
@CacheConfig(cacheNames = PRODUCT_CATEGORY_CACHE_NAME)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    @Cacheable(value = PRODUCT_CATEGORY_CACHE_NAME, key = "#categoryId")
    public ProductCategory findOne(Integer categoryId) {
        ProductCategory productCategory = productCategoryRepository.findOne(categoryId);
        if (productCategory == null) {
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        return productCategory;
    }

    @Override
    public List<ProductCategory> findByShopId(String shopId) {
        return productCategoryRepository.findByShopIdOrderByCategoryType(shopId);
    }

    @Override
    @CachePut(value = PRODUCT_CATEGORY_CACHE_NAME, key = "#productCategory.categoryId")
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    @CacheEvict(value = PRODUCT_CATEGORY_CACHE_NAME, key = "#categoryId")
    @Transactional(rollbackFor = SellException.class)
    public void deleteByCategoryId(Integer categoryId) {
        // 删除类目前需要对该类目的商品做处理
        // 下架商品，并修改商品类目为默认
        String categoryType = findOne(categoryId).getCategoryType();
        List<ProductInfo> productInfos = productInfoRepository.findByCategoryType(categoryType);
        for (ProductInfo productInfo : productInfos) {
            if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
                productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
            }
            productInfo.setCategoryType("-1");
            productInfoRepository.save(productInfo);
        }
        try {
            productCategoryRepository.deleteByCategoryId(categoryId);
        } catch (Exception e) {
            throw new SellException(ResultEnum.CATEGORY_DELETE_FAIL);
        }
    }

    @Override
    public Boolean checkCategoryTypeExist(String categoryType, String shopId) {
        ProductCategory productCategory = productCategoryRepository.findByCategoryTypeAndShopId(categoryType, shopId);
        return productCategory != null;
    }
}
