package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:43
 * Modified By:
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    int deleteByOrderId(String orderId);

    Page<OrderMaster> findByShopIdLikeAndOrderStatusLikeAndOrderIdLikeAndBuyerIdLikeAndBuyerNameLikeAndBuyerAddressLikeAndBuyerPhoneLikeAndOrderAmountBetweenOrderByCreateTimeDesc(
            String shopId, String orderStatus, String orderId, String buyerId, String buyerName, String buyerAddress, String buyerPhone, BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable
    );
}
