package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.dataobject.mapper.BuyerMapper;
import com.xukai.netshop.service.BuyerService;
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
    public void register(BuyerInfo buyerInfo) {
        // 检查username是否已存在
        BuyerInfo checkUsername = buyerMapper.checkUsername(buyerInfo.getUsername());

        int result = buyerMapper.register(buyerInfo);


    }
}
