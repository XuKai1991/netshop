package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.CartMaster;

/**
 * @author: Xukai
 * @description: 购物车接口
 * @createDate: 2018/9/26 14:14
 * @modified By:
 */
public interface CartMasterService {

    CartMaster findOne(String buyerId);
}
