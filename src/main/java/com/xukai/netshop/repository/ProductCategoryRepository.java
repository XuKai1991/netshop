package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 10:28
 * Modified By:
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    int deleteByCategoryId(Integer categoryId);

    ProductCategory findByCategoryTypeAndShopId(String categoryType, String shopId);

    List<ProductCategory> findByShopIdOrderByCategoryType(String shopId);
}
