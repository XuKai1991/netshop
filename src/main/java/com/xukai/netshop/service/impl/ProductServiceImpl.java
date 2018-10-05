package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.dto.CartDTO;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public Page<ProductInfo> findByCategory(Integer categoryType, Pageable pageable) {
        return productInfoRepository.findByCategoryTypeAndProductStatusOrderByCreateTimeDesc(categoryType, ProductStatusEnum.UP.getCode(), pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
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
            productInfoRepository.save(productInfo);
        }
    }

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
                productInfoRepository.save(productInfo);
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
        return productInfoRepository.save(productInfo);
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
        return productInfoRepository.save(productInfo);
    }
}
