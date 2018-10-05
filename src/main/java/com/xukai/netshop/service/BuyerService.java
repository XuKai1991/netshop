package com.xukai.netshop.service;


import com.xukai.netshop.dataobject.BuyerInfo;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/25 10:26
 * Modified By:
 */
public interface BuyerService {

    /**
     * 检查用户注册的username是否已经存在
     *
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    /**
     * 检查用户注册的email是否已经存在
     *
     * @param email
     * @return
     */
    boolean checkEmail(String email);

    /**
     * 用户注册
     *
     * @param buyerInfo
     */
    int save(BuyerInfo buyerInfo);

    /**
     * 根据用户名和密码查找用户
     *
     * @param username
     * @param password
     * @return
     */
    BuyerInfo findByUsernameAndPassword(String username, String password);
}
