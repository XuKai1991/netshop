package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.dto.CartDTO;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 16:04
 * Modified By:
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        return productInfo;
    }

    @Override
    public Page<ProductInfo> findUpAll(Pageable pageable) {
        return productInfoRepository.findByProductStatusOrderByCreateTimeDesc(ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    public Page<ProductInfo> findOnCondition(ProductInfo s_productInfo, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productInfoRepository.findByProductStatusNotAndProductIdLikeAndProductNameLikeAndProductPriceBetweenAndCategoryTypeLikeOrderByCreateTimeDesc(
                (s_productInfo.getProductStatus() == null || s_productInfo.getProductStatus() == -1) ? -1 : (1 - s_productInfo.getProductStatus()),
                "%" + (StringUtils.isEmpty(s_productInfo.getProductId()) ? "" : s_productInfo.getProductId()) + "%",
                "%" + (StringUtils.isEmpty(s_productInfo.getProductName()) ? "" : s_productInfo.getProductName()) + "%",
                minPrice == null ? new BigDecimal("0") : minPrice,
                maxPrice == null ? new BigDecimal("99999") : maxPrice,
                "%" + (StringUtils.isEmpty(s_productInfo.getCategoryType()) ? "" : s_productInfo.getCategoryType()) + "%",
                pageable
        );
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public Page<ProductInfo> findByCategory(Integer categoryType, Pageable pageable) {
        return productInfoRepository.findByCategoryTypeAndProductStatusOrderByCreateTimeDesc(categoryType, ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.saveAndFlush(productInfo);
    }

    /**
     * 增加库存
     *
     * @param cartDTOList
     */
    @Override
    @Transactional(rollbackFor = SellException.class)
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());
            productInfoRepository.saveAndFlush(productInfo);
        }
    }

    /**
     * 减少库存
     *
     * @param cartDTOList
     */
    @Override
    @Transactional(rollbackFor = SellException.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock = productInfo.getProductStock();
            Integer productQuantity = cartDTO.getProductQuantity();
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
