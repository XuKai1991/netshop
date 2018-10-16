package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.SellerInfo;
import com.xukai.netshop.repository.SellerInfoRepository;
import com.xukai.netshop.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/9 14:06
 * @modified By:
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByUsernameAndPassword(String username, String password) {
        SellerInfo sellerInfo = sellerInfoRepository.findByUsernameAndPassword(username, password);
        return sellerInfo;
    }
}
