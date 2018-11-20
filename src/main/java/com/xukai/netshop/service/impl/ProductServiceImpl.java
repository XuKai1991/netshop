package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.xukai.netshop.constant.CacheCons.PRODUCT_INFO_CACHE_NAME;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 16:04
 * Modified By:
 */
@Service
@Slf4j
@CacheConfig(cacheNames = PRODUCT_INFO_CACHE_NAME)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    @Cacheable(value = PRODUCT_INFO_CACHE_NAME, key = "#productId")
    public ProductInfo findOne(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        return productInfo;
    }

    @Override
    public Page<ProductInfo> findUpAll(String shopId, Pageable pageable) {
        return productInfoRepository.findByShopIdAndProductStatusOrderByCreateTimeDesc(shopId, ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    public Page<ProductInfo> findOnCondition(ProductInfo s_productInfo, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) == 1) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return productInfoRepository.findByProductStatusNotAndProductIdLikeAndProductNameLikeAndProductPriceBetweenAndCategoryTypeLikeAndShopIdLikeOrderByCreateTimeDesc(
                (s_productInfo.getProductStatus() == null || s_productInfo.getProductStatus() == -1) ? -1 : (1 - s_productInfo.getProductStatus()),
                "%" + (StringUtils.isEmpty(s_productInfo.getProductId()) ? "" : s_productInfo.getProductId()) + "%",
                "%" + (StringUtils.isEmpty(s_productInfo.getProductName()) ? "" : s_productInfo.getProductName()) + "%",
                minPrice == null ? new BigDecimal("0") : minPrice,
                maxPrice == null ? new BigDecimal("99999") : maxPrice,
                StringUtils.isEmpty(s_productInfo.getCategoryType()) ? "%%" : s_productInfo.getCategoryType(),
                StringUtils.isEmpty(s_productInfo.getShopId()) ? "%%" : s_productInfo.getShopId(),
                pageable
        );
    }

    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findAll();
    }

    @Override
    public Page<ProductInfo> findByCategory(String shopId, String categoryType, Pageable pageable) {
        return productInfoRepository.findByShopIdAndCategoryTypeAndProductStatusOrderByCreateTimeDesc(shopId, categoryType, ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    @CachePut(value = PRODUCT_INFO_CACHE_NAME, key = "#productInfo.productId")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.saveAndFlush(productInfo);
    }

    /**
     * 增加库存
     *
     * @param cartDetailList
     */
    @Override
    @Transactional(rollbackFor = SellException.class)
    public void increaseStock(List<CartDetail> cartDetailList) {
        for (CartDetail cartDetail : cartDetailList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock() + cartDetail.getProductQuantity());
            productInfoRepository.saveAndFlush(productInfo);
        }
    }

    /**
     * 减少库存
     *
     * @param cartDetailList
     */
    @Override
    @Transactional(rollbackFor = SellException.class)
    public void decreaseStock(List<CartDetail> cartDetailList) {
        for (CartDetail cartDetail : cartDetailList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock = productInfo.getProductStock();
            Integer productQuantity = cartDetail.getProductQuantity();
            if (productStock >= productQuantity) {
                productInfo.setProductStock(productStock - productQuantity);
                productInfoRepository.saveAndFlush(productInfo);
            } else {
                throw new SellException(ResultEnum.PRODUCT_NOT_ENOUGH);
            }
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.saveAndFlush(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.Down) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.Down.getCode());
        return productInfoRepository.saveAndFlush(productInfo);
    }
}
