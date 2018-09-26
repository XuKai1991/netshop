package com.xukai.netshop.controller;

import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.SellerInfo;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.SellerService;
import com.xukai.netshop.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Xukai
 * @description: 卖家用户
 * @createDate: 2018/7/9 17:40
 * @modified By:
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CookieConfig cookieConfig;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        SellerInfo sellerInfo;
        try {
            sellerInfo = sellerService.findSellerInfoByOpenid(username, password);
        } catch (SellException e) {
            log.error("【卖家端登录】发生异常{}", e);
            mav.addObject("msg", ResultEnum.LOGIN_FAIL.getMessage());
            mav.addObject("url", "/netshop/seller/order/list");
            mav.setViewName("common/error");
            return mav;
        }
        // 设置token至cookie
        CookieUtils.set(cookieConfig.getToken(), username, cookieConfig.getExpire(), response);
        mav.setViewName("redirect:" + "http://localhost/netshop/seller/order/list");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        mav.addObject("url", "/sell/seller/order/list");
        mav.setViewName("common/success");
        return mav;
    }

}
