package com.xukai.netshop.repository;

import com.xukai.netshop.dataobject.BuyerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/31 01:41
 * @modified By:
 */
public interface BuyerAddressRepository extends JpaRepository<BuyerAddress, String> {

    List<BuyerAddress> findByBuyerId(String buyerId);
}
