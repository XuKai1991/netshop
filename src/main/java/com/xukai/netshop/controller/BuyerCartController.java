package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.CartMaster;
import com.xukai.netshop.service.BuyerCartService;
import com.xukai.netshop.utils.ResultVOUtil;
import com.xukai.netshop.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 14:04
 * @modified By:
 */
@RestController
@RequestMapping("/buyer/cart")
@Slf4j
public class BuyerCartController {

    @Autowired
    private CookieConfig cookieConfig;

    @Autowired
    private BuyerCartService buyerCartService;

    /**
     * 获取买家的购物车数据列表
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ResultVO findByBuyerId(HttpServletRequest request, String shopId) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        CartMaster cartMaster = buyerCartService.list(buyerId, shopId);
        return ResultVOUtil.success(cartMaster);
    }

    /**
     * 添加商品到购物车
     *
     * @param cartDetail
     * @param request
     */
    @PostMapping("/add")
    public ResultVO addToCart(CartDetail cartDetail, String shopId, HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        buyerCartService.addItem(cartDetail, buyerId, shopId);
        return ResultVOUtil.success();
    }

    /**
     * 增加购物车内的商品数量
     *
     * @param itemId
     * @param request
     */
    @GetMapping("/increase")
    public ResultVO increaseCartItemNum(String itemId, String shopId, HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        buyerCartService.increaseItemNum(itemId, buyerId, shopId);
        return ResultVOUtil.success();
    }

    /**
     * 减少购物车内的商品数量
     *
     * @param itemId
     * @param request
     */
    @GetMapping("/decrease")
    public ResultVO decreaseCartItemNum(String itemId, String shopId, HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        buyerCartService.decreaseItemNum(itemId, buyerId, shopId);
        return ResultVOUtil.success();
    }

    /**
     * 用户直接修改购物车内商品数量
     *
     * @param itemId
     * @param quantity
     * @param request
     * @return
     */
    @GetMapping("/editQuantity")
    public ResultVO editItemNum(String itemId, Integer quantity, String shopId, HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        buyerCartService.editItemNum(itemId, quantity, buyerId, shopId);
        return ResultVOUtil.success();
    }

    /**
     * 单条删除购物车中商品
     *
     * @param itemId
     * @param request
     */
    @GetMapping("/delete")
    public ResultVO deleteCartItem(String itemId, String shopId, HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        buyerCartService.deleteItem(itemId, buyerId, shopId);
        return ResultVOUtil.success();
    }

    /**
     * 批量删除购物车中商品
     *
     * @param itemIds
     * @param request
     */
    @GetMapping("/batchDelete")
    public ResultVO deleteCartItems(String itemIds, String shopId, HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        buyerCartService.deleteItems(itemIds, buyerId, shopId);
        return ResultVOUtil.success();
    }
}
