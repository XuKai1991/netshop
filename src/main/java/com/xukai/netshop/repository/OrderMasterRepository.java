package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:43
 * Modified By:
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerIdOrderByCreateTimeDesc(String buyerId, Pageable pageable);

    Page<OrderMaster> findByCreateTimeBeforeOrderByCreateTimeDesc(Date date, Pageable pageable);

    int deleteByOrderId(String orderId);
}
