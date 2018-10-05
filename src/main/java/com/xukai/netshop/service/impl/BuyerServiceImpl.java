package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.dataobject.mapper.BuyerMapper;
import com.xukai.netshop.service.BuyerService;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/25 11:43
 * Modified By:
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerMapper buyerMapper;

    @Override
    public boolean checkUsername(String username) {
        BuyerInfo checkUsername = buyerMapper.checkUsername(username);
        return checkUsername == null ? false : true;
    }

    @Override
    public boolean checkEmail(String email) {
        BuyerInfo checkEmail = buyerMapper.checkEmail(email);
        return checkEmail == null ? false : true;
    }

    @Override
    public int save(BuyerInfo buyerInfo) {
        buyerInfo.setBuyerId(KeyUtils.genUUID());
        int result = buyerMapper.register(buyerInfo);
        return result;
    }

    @Override
    public BuyerInfo findByUsernameAndPassword(String username, String password) {
        return buyerMapper.findByUsernameAndPassword(username, password);
    }
}
