package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.BuyerAddress;

import java.util.List;

/**
 * @author: Xukai
 * @description: 买家常用地址管理service
 * @createDate: 2018/10/31 01:44
 * @modified By:
 */
public interface BuyerAddressService {

    /**
     * 保存地址
     *
     * @param buyerAddress
     * @return
     */
    BuyerAddress save(BuyerAddress buyerAddress);

    /**
     * 根据主键查找
     *
     * @param buyerAddressId
     * @return
     */
    BuyerAddress findOne(String buyerAddressId);

    /**
     * 根据buyerId查找
     *
     * @param buyerId
     * @return
     */
    List<BuyerAddress> findByBuyerId(String buyerId);
}
