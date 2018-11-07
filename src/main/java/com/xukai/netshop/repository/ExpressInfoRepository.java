package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.ExpressInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Xukai
 * @description: 快递repository
 * @createDate: 2018/11/5 21:41
 * @modified By:
 */
public interface ExpressInfoRepository extends JpaRepository<ExpressInfo, String> {

    /**
     * 根据orderId删除
     *
     * @param orderId
     * @return
     */
    int deleteByOrderId(String orderId);

    /**
     * 根据快递状态查找
     *
     * @param expressStatus
     * @return
     */
    List<ExpressInfo> findByExpressStatus(Integer expressStatus);
}
