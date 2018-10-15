package com.xukai.netshop.repository;


import com.xukai.netshop.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 14:16
 * Modified By:
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    Page<ProductInfo> findByCategoryTypeAndProductStatusOrderByCreateTimeDesc(Integer categoryType, Integer productStatus, Pageable pageable);

    Page<ProductInfo> findByProductStatusOrderByCreateTimeDesc(Integer productStatus, Pageable pageable);

    List<ProductInfo> findByCategoryType(String categoryType);

    Page<ProductInfo> findByProductStatusNotAndProductIdLikeAndProductNameLikeAndProductPriceBetweenAndCategoryTypeLikeOrderByCreateTimeDesc(
            Integer productStatus, String productId, String productName, BigDecimal minPrice, BigDecimal maxPrice, String categoryType, Pageable pageable
    );
}
