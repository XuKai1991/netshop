package com.xukai.netshop.controller;

import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.dataobject.SellerInfo;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.BuyerService;
import com.xukai.netshop.service.SellerService;
import com.xukai.netshop.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author: Xukai
 * @description: 卖家用户
 * @createDate: 2018/7/9 17:40
 * @modified By:
 */
@RestController
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CookieConfig cookieConfig;

    @Autowired
    private BaseUrlConfig baseUrlConfig;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sell/login/login");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        SellerInfo sellerInfo;
        try {
            sellerInfo = sellerService.findByUsernameAndPassword(username, password);
        } catch (SellException e) {
            log.error("【卖家端登录】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/");
            mav.setViewName("sell/common/error");
            return mav;
        }
        // 设置token至cookie
        HashMap<String, String> fieldsMap = new HashMap<>(2);
        fieldsMap.put(cookieConfig.getSellerId(), String.valueOf(sellerInfo.getId()));
        TokenUtils.addLoginTrace(fieldsMap, cookieConfig, request, response);
        String sellRequestURI = (String) request.getSession().getAttribute("sellRequestURI");
        if (StringUtils.isNotEmpty(sellRequestURI)) {
            mav.setViewName("redirect:" + baseUrlConfig.getBackBaseUrl() + sellRequestURI);
        } else {
            mav.setViewName("redirect:" + baseUrlConfig.getBackBaseUrl() + "/seller/order/list");
        }
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        // 清除cookie和session
        String[] fields = {cookieConfig.getSellerId()};
        TokenUtils.cleanLoginTrace(fields, request, response);
        mav.addObject("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        mav.addObject("url", "/seller/");
        mav.setViewName("sell/common/success");
        return mav;
    }

    @GetMapping("/listBuyer")
    public ModelAndView adminBuyerInfo(BuyerInfo s_buyer,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ModelAndView mav = new ModelAndView("sell/buyer/list");
        Page<BuyerInfo> buyerInfoPage = buyerService.findOnCondition(s_buyer, new PageRequest(page - 1, size));
        mav.addObject("s_buyer", s_buyer);
        mav.addObject("buyerInfoPage", buyerInfoPage);
        mav.addObject("currentPage", page);
        mav.addObject("size", size);
        return mav;
    }

    @GetMapping("/deleteBuyer")
    public ModelAndView deleteBuyerInfo(@RequestParam("buyerId") String buyerId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/seller/listBuyer");
        try {
            buyerService.deleteByBuyerId(buyerId);
        } catch (SellException e) {
            log.error("【卖家端删除买家用户】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.BUYER_DELETE_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }

}
