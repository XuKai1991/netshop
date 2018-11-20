package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.CartMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 14:12
 * @modified By:
 */
public interface CartMasterRepository extends JpaRepository<CartMaster, String> {

    CartMaster findByBuyerIdAndShopId(String buyerId, String shopId);

}
