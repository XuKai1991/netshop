package com.xukai.netshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.CartMaster;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.repository.CartMasterRepository;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.BuyerCartService;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Xukai
 * @description: 购物车接口实现
 * @createDate: 2018/9/26 14:15
 * @modified By:
 */
@Service
@Slf4j
public class BuyerCartServiceImpl implements BuyerCartService {

    @Autowired
    private CartMasterRepository cartMasterRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public CartMaster list(String buyerId) {
        CartMaster cartMaster = cartMasterRepository.findByBuyerId(buyerId);
        if (cartMaster == null) {
            cartMaster = add(buyerId);
        } else {
            // 需要根据购物车中的商品ID重新查询商品是否在售，如果有改变就需要更新购物车内容
            String cartItems = cartMaster.getCartItems();
            if (StringUtils.isNotEmpty(cartItems)) {
                List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
                ArrayList<CartDetail> toDelete = new ArrayList<>();
                for (CartDetail item : itemList) {
                    ProductInfo productInfo = productInfoRepository.findOne(item.getProductId());
                    // 如果找不到商品，从购物车删除
                    if (productInfo == null) {
                        toDelete.add(item);
                    } else {
                        // 如果商品已下架，从购物车删除
                        if (!productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
                            toDelete.add(item);
                        } else {
                            item.setProductStatus(productInfo.getProductStatus());
                            item.setProductPrice(productInfo.getProductPrice());
                            item.setProductImgMd(productInfo.getProductImgMd());
                            item.setProductName(productInfo.getProductName());
                            item.setProductImgMd(productInfo.getProductImgMd());
                        }
                    }
                }
                itemList.removeAll(toDelete);
                cartMaster.setCartItems(JSONObject.toJSONString(itemList));
                cartMasterRepository.save(cartMaster);
            }
        }
        return cartMaster;
    }

    @Override
    public CartMaster add(String buyerId) {
        CartMaster cartMaster = new CartMaster(buyerId, "");
        CartMaster master = cartMasterRepository.save(cartMaster);
        return master;
    }

    @Override
    public CartMaster addItem(CartDetail cartDetail, String buyerId) {
        CartMaster cartMaster = list(buyerId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList;
        // 判断购物车是否为空
        if (StringUtils.isNotEmpty(cartItems)) {
            itemList = JSONObject.parseArray(cartItems, CartDetail.class);
            // 商品已存在购物车标识
            boolean existFlag = false;
            for (CartDetail item : itemList) {
                // 颜色和尺码有一项不同的同一个商品，在购物车都算不同的商品
                if (cartDetail.getProductId().equals(item.getProductId()) && cartDetail.getProductColor().equals(item.getProductColor()) && cartDetail.getProductSize().equals(item.getProductSize())) {
                    // 如果货号、颜色、尺码都相同，就在原来基础上增加商品数量
                    int quantity = item.getProductQuantity() + cartDetail.getProductQuantity();
                    item.setProductQuantity(quantity);
                    existFlag = true;
                    break;
                }
            }
            if (!existFlag) {
                cartDetail.setItemId(KeyUtils.genUniqueKey());
                cartDetail.setProductStatus(0);
                itemList.add(cartDetail);
            }
        } else {
            itemList = new ArrayList<>();
            cartDetail.setItemId(KeyUtils.genUniqueKey());
            cartDetail.setProductStatus(0);
            itemList.add(cartDetail);
        }
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        return cartMasterRepository.save(cartMaster);
    }

    @Override
    public CartMaster deleteItem(String itemId, String buyerId) {
        CartMaster cartMaster = list(buyerId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                itemList.remove(item);
                break;
            }
        }
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        return cartMasterRepository.save(cartMaster);
    }

    @Override
    public CartMaster deleteItems(String itemIds, String buyerId) {
        List<String> itemIdList = Arrays.asList(itemIds.split("_"));
        CartMaster cartMaster = list(buyerId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        List<CartDetail> toDel = new ArrayList<>();
        for (CartDetail item : itemList) {
            for (String itemId : itemIdList) {
                if (item.getItemId().equals(itemId)) {
                    toDel.add(item);
                    break;
                }
            }
        }
        itemList.removeAll(toDel);
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        return cartMasterRepository.save(cartMaster);
    }

    @Override
    public CartMaster increaseItemNum(String itemId, String buyerId) {
        CartMaster cartMaster = list(buyerId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                Integer quantity = item.getProductQuantity();
                item.setProductQuantity(quantity + 1);
                break;
            }
        }
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        return cartMasterRepository.save(cartMaster);
    }

    @Override
    public CartMaster decreaseItemNum(String itemId, String buyerId) {
        CartMaster cartMaster = list(buyerId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                Integer quantity = item.getProductQuantity();
                // 如果商品只剩一条，直接删除
                if (quantity == 1) {
                    itemList.remove(item);
                } else {
                    item.setProductQuantity(quantity - 1);
                }
                break;
            }
        }
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        return cartMasterRepository.save(cartMaster);
    }

    @Override
    public CartMaster editItemNum(String itemId, Integer quantity, String buyerId) {
        CartMaster cartMaster = list(buyerId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                item.setProductQuantity(quantity);
                break;
            }
        }
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        return cartMasterRepository.save(cartMaster);
    }
}
