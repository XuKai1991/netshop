package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.BuyerAddress;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
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
    public void save(BuyerAddress buyerAddress) {
        if (StringUtils.isEmpty(buyerAddress.getBuyerAddressId())) {
            buyerAddress.setBuyerAddressId(KeyUtils.genUniqueKey());
        }
        BuyerAddress save = buyerAddressRepository.save(buyerAddress);
        if (save == null) {
            log.error("【保存常用地址】发生异常{}", ResultEnum.BUYER_ADDRESS_SAVE_FAIL.getMessage());
            throw new BuyException(ResultEnum.BUYER_ADDRESS_SAVE_FAIL);
        }
    }

    @Override
    public BuyerAddress findOne(String buyerAddressId) {
        BuyerAddress buyerAddress = buyerAddressRepository.findOne(buyerAddressId);
        if (buyerAddress == null) {
            log.error("【查看常用地址】发生异常{}", ResultEnum.BUYER_ADDRESS_NOT_EXIST.getMessage());
            throw new BuyException(ResultEnum.BUYER_ADDRESS_NOT_EXIST);
        }
        return buyerAddress;
    }

    @Override
    public List<BuyerAddress> findByBuyerId(String buyerId) {
        List<BuyerAddress> buyerAddressList = buyerAddressRepository.findByBuyerId(buyerId);
        return buyerAddressList;
    }
}
