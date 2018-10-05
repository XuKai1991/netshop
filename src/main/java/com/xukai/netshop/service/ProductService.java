package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 15:56
 * Modified By:
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询在售的全部商品
     * @return
     */
    Page<ProductInfo> findUpAll(Pageable pageable);

    /**
     * 查询所有商品，带分页
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 查询特定类别下的商品，带分页
     * @param pageable
     * @return
     */
    Page<ProductInfo> findByCategory(Integer categoryType, Pageable pageable);

    /**
     * 保存商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);

}
