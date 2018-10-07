package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.service.BuyerService;
import com.xukai.netshop.utils.CookieUtils;
import com.xukai.netshop.utils.ResultVOUtil;
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
 * @description:
 * @createDate: 2018/9/26 14:28
 * @modified By:
 */
@RestController
@RequestMapping("/buyer")
@Slf4j
public class BuyerUserController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CookieConfig cookieConfig;

    @Autowired
    private BaseUrlConfig baseUrlConfig;

    /**
     * 跳转登录页
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("buy/login/login");
        // ModelAndView mav = new ModelAndView("buy/index");
        return mav;
    }

    /**
     * 使用ajax检查用户注册的username是否已经存在于数据库
     *
     * @param username
     * @return
     */
    @GetMapping("/checkUsername")
    public ResultVO checkUsername(@RequestParam("username") String username) {
        boolean flag = buyerService.checkUsername(username);
        if (flag) {
            return ResultVOUtil.error(0, "fail");
        } else {
            return ResultVOUtil.success();
        }
    }

    /**
     * 使用ajax检查用户注册的email是否已经存在于数据库
     *
     * @param email
     * @return
     */
    @GetMapping("/checkEmail")
    public ResultVO checkEmail(@RequestParam("email") String email) {
        boolean flag = buyerService.checkEmail(email);
        if (flag) {
            return ResultVOUtil.error(0, "fail");
        } else {
            return ResultVOUtil.success();
        }
    }

    /**
     * 用户注册
     *
     * @param buyerInfo
     * @return
     */
    @PostMapping("/save")
    public ResultVO save(BuyerInfo buyerInfo) {
        int result = buyerService.save(buyerInfo);
        if (result != 1) {
            log.error("【买家端保存用户信息】发生异常{}");
            throw new BuyException(ResultEnum.BUYER_REGISTER_FAIL);
        }
        log.info("【买家端保存用户信息】注册成功");
        return ResultVOUtil.success();
    }

    /**
     * 找回密码
     *
     * @param email
     * @return
     */
    @GetMapping("/getBackPwd")
    public ModelAndView getBackPassword(String email) {
        ModelAndView mav = new ModelAndView();
        return mav;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        BuyerInfo buyerInfo = buyerService.findByUsernameAndPassword(username, password);
        if (buyerInfo == null) {
            throw new BuyException(ResultEnum.BUYER_LOGIN_FAIL);
        }
        // 设置token至cookie
        CookieUtils.set(cookieConfig.getBuyerId(), buyerInfo.getBuyerId(), cookieConfig.getExpire(), response);
        CookieUtils.set(cookieConfig.getBuyerName(), buyerInfo.getUsername(), cookieConfig.getExpire(), response);
        String buyRequestURI = (String) request.getSession().getAttribute("buyRequestURI");
        if (StringUtils.isNotEmpty(buyRequestURI)) {
            mav.setViewName("redirect:" + baseUrlConfig.getBack_base_url() + buyRequestURI);
        } else {
            mav.setViewName("redirect:" + baseUrlConfig.getBack_base_url() + "/buyer/index");
        }
        return mav;
    }

    /**
     * 注销
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request, HttpServletResponse response) {
        // 从cookie里查询
        Cookie cookie = CookieUtils.get(cookieConfig.getBuyerId(), request);
        if (cookie != null) {
            // 清除cookie
            CookieUtils.set(cookieConfig.getBuyerId(), null, 0, response);
            CookieUtils.set(cookieConfig.getBuyerName(), null, 0, response);
        }
        return ResultVOUtil.success();
    }
}
