package com.xukai.netshop.controller;

import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.SellerInfo;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.SellerService;
import com.xukai.netshop.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            mav.addObject("msg", ResultEnum.LOGIN_FAIL.getMessage());
            mav.addObject("url", "/netshop/seller/");
            mav.setViewName("sell/common/error");
            return mav;
        }
        // 设置token至cookie
        CookieUtils.set(cookieConfig.getSellerId(), sellerInfo.getId(), cookieConfig.getExpire(), request, response);
        String sellRequestURI = (String) request.getSession().getAttribute("sellRequestURI");
        if (StringUtils.isNotEmpty(sellRequestURI)) {
            mav.setViewName("redirect:" + baseUrlConfig.getBack_base_url() + sellRequestURI);
        } else {
            mav.setViewName("redirect:" + baseUrlConfig.getBack_base_url() + "/netshop/seller/order/list");
        }
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        // 从cookie里查询
        Cookie cookie = CookieUtils.get(cookieConfig.getSellerId(), request);
        if (cookie != null) {
            // 清除cookie
            CookieUtils.set(cookieConfig.getSellerId(), null, 0, request, response);
        }
        mav.addObject("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        mav.addObject("url", "/netshop/seller/");
        mav.setViewName("sell/common/success");
        return mav;
    }

}
