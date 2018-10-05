package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.SellerInfo;

/**
 * @author: Xukai
 * @description: 卖家端service接口
 * @createDate: 2018/7/9 14:04
 * @modified By:
 */
public interface SellerService {

    SellerInfo findByUsernameAndPassword(String username, String password);

}
