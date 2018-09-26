package com.xukai.netshop.repository;


import com.xukai.netshop.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 14:16
 * Modified By:
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatusIn(Integer productStatus);
}
