package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:43
 * Modified By:
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerId(String buyerId, Pageable pageable);
}
