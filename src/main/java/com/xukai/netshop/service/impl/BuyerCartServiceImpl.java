package com.xukai.netshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.CartMaster;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.repository.CartMasterRepository;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.BuyerCartService;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public CartMaster list(String buyerId, String shopId) {
        CartMaster cartMaster = cartMasterRepository.findByBuyerIdAndShopId(buyerId, shopId);
        if (cartMaster == null) {
            cartMaster = add(buyerId, shopId);
        } else {
            // 需要根据购物车中的商品ID重新查询商品是否在售，如果有改变就需要更新购物车内容
            String cartItems = cartMaster.getCartItems();
            if (StringUtils.isNotEmpty(cartItems)) {
                List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
                ArrayList<CartDetail> toDelete = new ArrayList<>();
                ProductInfo productInfo;
                for (CartDetail item : itemList) {
                    productInfo = productInfoRepository.findOne(item.getProductId());
                    // 如果找不到商品，从购物车删除
                    if (productInfo == null) {
                        toDelete.add(item);
                    } else {
                        // 如果商品已下架，从购物车删除
                        if (!productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
                            toDelete.add(item);
                        } else {
                            item.setProductPrice(productInfo.getProductPrice());
                            item.setProductImgMd(productInfo.getProductImgMd());
                            item.setProductName(productInfo.getProductName());
                            item.setProductImgMd(productInfo.getProductImgMd());
                            // 购物车商品数量超过商品库存，则设置为库存量
                            if (item.getProductQuantity() > productInfo.getProductStock()) {
                                item.setProductQuantity(productInfo.getProductStock());
                            }
                        }
                    }
                }
                itemList.removeAll(toDelete);
                // 如果物品数量超过库存，超库存的物品数量全部设置为1
                HashMap<String, Integer> quantityByProductIdMap = new HashMap<>();
                for (CartDetail detail : itemList) {
                    if (quantityByProductIdMap.containsKey(detail.getProductId())) {
                        Integer quantity = quantityByProductIdMap.get(detail.getProductId()) + detail.getProductQuantity();
                        quantityByProductIdMap.put(detail.getProductId(), quantity);
                    } else {
                        quantityByProductIdMap.put(detail.getProductId(), detail.getProductQuantity());
                    }
                }
                ArrayList<String> overStockProductIdList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : quantityByProductIdMap.entrySet()) {
                    productInfo = productInfoRepository.findOne(entry.getKey());
                    if (entry.getValue() > productInfo.getProductStock()) {
                        overStockProductIdList.add(entry.getKey());
                    }
                }
                for (CartDetail cartDetail : itemList) {
                    if (overStockProductIdList.contains(cartDetail.getProductId())) {
                        cartDetail.setProductQuantity(1);
                    }
                }
                cartMaster.setCartItems(JSONObject.toJSONString(itemList));
                cartMasterRepository.save(cartMaster);
            }
        }
        return cartMaster;
    }

    @Override
    public CartMaster add(String buyerId, String shopId) {
        CartMaster cartMaster = new CartMaster(buyerId, shopId, "");
        CartMaster master = cartMasterRepository.save(cartMaster);
        return master;
    }

    @Override
    public void addItem(CartDetail cartDetail, String buyerId, String shopId) {
        CartMaster cartMaster = list(buyerId, shopId);
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
                cartDetail.setProductStatus(ProductStatusEnum.UP.getCode());
                itemList.add(cartDetail);
            }
        } else {
            itemList = new ArrayList<>();
            cartDetail.setItemId(KeyUtils.genUniqueKey());
            cartDetail.setProductStatus(ProductStatusEnum.UP.getCode());
            itemList.add(cartDetail);
        }
        // 判断购物车内物品数量是否超过库存
        judgeProductStock(itemList);
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        CartMaster save = cartMasterRepository.save(cartMaster);
        if (save == null) {
            throw new BuyException(ResultEnum.CART_ADD_ITEM_FAIL);
        }
    }

    @Override
    public void deleteItem(String itemId, String buyerId, String shopId) {
        CartMaster cartMaster = list(buyerId, shopId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                itemList.remove(item);
                break;
            }
        }
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        CartMaster save = cartMasterRepository.save(cartMaster);
        if (save == null) {
            throw new BuyException(ResultEnum.CART_DELETE_ITEM_FAIL);
        }
    }

    @Override
    public void deleteItems(String itemIds, String buyerId, String shopId) {
        List<String> itemIdList = Arrays.asList(itemIds.split("_"));
        CartMaster cartMaster = list(buyerId, shopId);
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
        CartMaster save = cartMasterRepository.save(cartMaster);
        if (save == null) {
            throw new BuyException(ResultEnum.CART_DELETE_ITEM_FAIL);
        }
    }

    @Override
    public void increaseItemNum(String itemId, String buyerId, String shopId) {
        CartMaster cartMaster = list(buyerId, shopId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                Integer quantity = item.getProductQuantity();
                item.setProductQuantity(quantity + 1);
                break;
            }
        }
        judgeProductStock(itemList);
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        CartMaster save = cartMasterRepository.save(cartMaster);
        if (save == null) {
            throw new BuyException(ResultEnum.CART_INCREASE_ITEM_FAIL);
        }
    }

    @Override
    public void decreaseItemNum(String itemId, String buyerId, String shopId) {
        CartMaster cartMaster = list(buyerId, shopId);
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
        CartMaster save = cartMasterRepository.save(cartMaster);
        if (save == null) {
            throw new BuyException(ResultEnum.CART_DECREASE_ITEM_FAIL);
        }
    }

    @Override
    public void editItemNum(String itemId, Integer quantity, String buyerId, String shopId) {
        CartMaster cartMaster = list(buyerId, shopId);
        String cartItems = cartMaster.getCartItems();
        List<CartDetail> itemList = JSONObject.parseArray(cartItems, CartDetail.class);
        for (CartDetail item : itemList) {
            if (item.getItemId().equals(itemId)) {
                item.setProductQuantity(quantity);
                break;
            }
        }
        judgeProductStock(itemList);
        cartMaster.setCartItems(JSONObject.toJSONString(itemList));
        CartMaster save = cartMasterRepository.save(cartMaster);
        if (save == null) {
            throw new BuyException(ResultEnum.CART_EDIT_ITEM_FAIL);
        }
    }

    /**
     * 判断购物车内商品是否超过库存
     * 此时以productId为判断基础，相同即为同一商品
     *
     * @param itemList
     */
    public void judgeProductStock(List<CartDetail> itemList) {
        HashMap<String, Integer> quantityByProductIdMap = new HashMap<>();
        for (CartDetail detail : itemList) {
            if (quantityByProductIdMap.containsKey(detail.getProductId())) {
                Integer quantity = quantityByProductIdMap.get(detail.getProductId()) + detail.getProductQuantity();
                quantityByProductIdMap.put(detail.getProductId(), quantity);
            } else {
                quantityByProductIdMap.put(detail.getProductId(), detail.getProductQuantity());
            }
        }
        ProductInfo productInfo;
        for (Map.Entry<String, Integer> entry : quantityByProductIdMap.entrySet()) {
            productInfo = productInfoRepository.findOne(entry.getKey());
            if (entry.getValue() > productInfo.getProductStock()) {
                throw new BuyException(ResultEnum.PRODUCT_NOT_ENOUGH);
            }
        }
    }
}
