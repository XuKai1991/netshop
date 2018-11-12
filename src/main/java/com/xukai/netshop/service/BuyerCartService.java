package com.xukai.netshop.service;

import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.CartMaster;

/**
 * @author: Xukai
 * @description: 购物车接口
 * @createDate: 2018/9/26 14:14
 * @modified By:
 */
public interface BuyerCartService {

    /**
     * 根据buyerId查找购物车数据
     *
     * @param buyerId
     * @return
     */
    CartMaster list(String buyerId);

    /**
     * 为买家创建购物车
     *
     * @param buyerId
     * @return
     */
    CartMaster add(String buyerId);

    /**
     * 向购物车添加内容
     *
     * @param cartDetail
     * @param buyerId
     * @return
     */
    void addItem(CartDetail cartDetail, String buyerId);

    /**
     * 根据单条itemId删除购物车中的内容
     *
     * @param itemId
     * @param buyerId
     * @return
     */
    void deleteItem(String itemId, String buyerId);

    /**
     * 根据itemIds批量删除购物车中的内容
     *
     * @param itemIds
     * @param buyerId
     */
    void deleteItems(String itemIds, String buyerId);

    /**
     * 增加购物车中某条商品的数量
     *
     * @param itemId
     * @param buyerId
     * @return
     */
    void increaseItemNum(String itemId, String buyerId);

    /**
     * 减少购物车中某条商品的数量
     *
     * @param itemId
     * @param buyerId
     * @return
     */
    void decreaseItemNum(String itemId, String buyerId);

    /**
     * 直接修改购物车内商品数量
     *
     * @param itemId
     * @param quantity
     * @param buyerId
     * @return
     */
    void editItemNum(String itemId, Integer quantity, String buyerId);
}
