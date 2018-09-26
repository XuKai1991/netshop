package com.xukai.netshop.repository;


import com.xukai.netshop.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 10:45
 * Modified By:
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
