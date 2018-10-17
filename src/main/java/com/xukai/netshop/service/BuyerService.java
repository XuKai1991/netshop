package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.BuyerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @return
     */
    BuyerInfo save(BuyerInfo buyerInfo);

    /**
     * 根据用户名和密码查找用户
     *
     * @param username
     * @param password
     * @return
     */
    BuyerInfo findByUsernameAndPassword(String username, String password);

    /**
     * 找回密码
     *
     * @param email
     * @return
     */
    void getBackPassword(String email);

    /**
     * 根据条件查找买家信息
     *
     * @param s_buyer
     * @param pageable
     * @return
     */
    Page<BuyerInfo> findOnCondition(BuyerInfo s_buyer, Pageable pageable);

    /**
     * 根据买家ID删除买家
     *
     * @param buyerId
     */
    void deleteByBuyerId(String buyerId);

    /**
     * 根据买家ID查找用户
     *
     * @param buyerId
     * @return
     */
    BuyerInfo findByBuyerId(String buyerId);
}
