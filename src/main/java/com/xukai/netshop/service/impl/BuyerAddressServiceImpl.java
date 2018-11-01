package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.BuyerAddress;
import com.xukai.netshop.repository.BuyerAddressRepository;
import com.xukai.netshop.service.BuyerAddressService;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Xukai
 * @description: 家常用地址管理service实现类
 * @createDate: 2018/10/31 01:44
 * @modified By:
 */
@Service
@Slf4j
public class BuyerAddressServiceImpl implements BuyerAddressService {

    @Autowired
    private BuyerAddressRepository buyerAddressRepository;

    @Override
    public BuyerAddress save(BuyerAddress buyerAddress) {
        if (StringUtils.isEmpty(buyerAddress.getBuyerAddressId())) {
            buyerAddress.setBuyerAddressId(KeyUtils.genUniqueKey());
        }
        return buyerAddressRepository.save(buyerAddress);
    }

    @Override
    public BuyerAddress findOne(String buyerAddressId) {
        BuyerAddress buyerAddress = buyerAddressRepository.findOne(buyerAddressId);
        return buyerAddress;
    }

    @Override
    public List<BuyerAddress> findByBuyerId(String buyerId) {
        List<BuyerAddress> buyerAddressList = buyerAddressRepository.findByBuyerId(buyerId);
        return buyerAddressList;
    }
}
