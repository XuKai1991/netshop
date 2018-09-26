package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.CartMaster;
import com.xukai.netshop.repository.CartMasterRepository;
import com.xukai.netshop.service.CartMasterService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Xukai
 * @description: 购物车接口实现
 * @createDate: 2018/9/26 14:15
 * @modified By:
 */
public class CartMasterServiceImpl implements CartMasterService {

    @Autowired
    private CartMasterRepository cartMasterRepository;

    @Override
    public CartMaster findOne(String buyerId) {
        CartMaster cartMaster = cartMasterRepository.findByBuyerId(buyerId);
        if(cartMaster == null){
            cartMaster = new CartMaster();
            cartMaster.setBuyerId(buyerId);
        }


        return null;
    }
}
