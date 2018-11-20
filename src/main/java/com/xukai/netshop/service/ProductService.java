package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 15:56
 * Modified By:
 */
public interface ProductService {

    /**
     * 根据主键ID查询商品
     *
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询在售的全部商品
     *
     * @param shopId
     * @param pageable
     * @return
     */
    Page<ProductInfo> findUpAll(String shopId, Pageable pageable);

    /**
     * 按条件搜索商品
     *
     * @param s_productInfo
     * @param minPrice
     * @param maxPrice
     * @param pageable
     * @return
     */
    Page<ProductInfo> findOnCondition(ProductInfo s_productInfo, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * 查询所有商品
     *
     * @return
     */
    List<ProductInfo> findAll();

    /**
     * 查询特定类别下的商品，带分页
     *
     * @param shopId
     * @param categoryType
     * @param pageable
     * @return
     */
    Page<ProductInfo> findByCategory(String shopId, String categoryType, Pageable pageable);

    /**
     * 保存商品
     *
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     *
     * @param cartDetailList
     */
    void increaseStock(List<CartDetail> cartDetailList);

    /**
     * 减库存
     *
     * @param cartDetailList
     */
    void decreaseStock(List<CartDetail> cartDetailList);

    /**
     * 上架
     *
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架
     *
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);

}
