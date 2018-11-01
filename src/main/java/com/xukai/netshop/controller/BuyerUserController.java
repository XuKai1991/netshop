package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.BuyerService;
import com.xukai.netshop.utils.TokenUtils;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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

    /**
     * 跳转登录页
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("buy/login/login");
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
        BuyerInfo save = buyerService.save(buyerInfo);
        if (save == null) {
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
    public ResultVO getBackPassword(@RequestParam("email") String email) {
        buyerService.getBackPassword(email);
        return ResultVOUtil.success();
    }

    @GetMapping("/test")
    public ModelAndView getBackPassword() {
        ModelAndView mav = new ModelAndView("buy/login/test");
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
    public ResultVO login(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request, HttpServletResponse response) {
        BuyerInfo buyerInfo = buyerService.findByUsernameAndPassword(username, password);
        if (buyerInfo == null) {
            throw new BuyException(ResultEnum.BUYER_LOGIN_FAIL);
        }
        // 设置token至cookie和session
        HashMap<String, String> fieldsMap = new HashMap<>(2);
        fieldsMap.put(cookieConfig.getBuyerId(), buyerInfo.getBuyerId());
        fieldsMap.put(cookieConfig.getBuyerName(), buyerInfo.getUsername());
        TokenUtils.addLoginTrace(fieldsMap, cookieConfig, request, response);
        return ResultVOUtil.success();
    }

    /**
     * 修改买家信息
     *
     * @param newPassword
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/editPwd")
    public ResultVO edit(@RequestParam("newPassword") String newPassword,
                         HttpServletRequest request, HttpServletResponse response) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        if (StringUtils.isEmpty(buyerId)) {
            log.error("【买家端修改用户密码】{}", ResultEnum.BUYER_NOT_LOGIN);
            throw new BuyException(ResultEnum.PARAM_ERROR);
        }
        BuyerInfo existBuyerInfo = buyerService.findByBuyerId(buyerId);
        if (existBuyerInfo == null) {
            log.error("【买家端修改用户密码】发生异常{}", ResultEnum.BUYER_NOT_EXIST.getMessage());
            throw new BuyException(ResultEnum.BUYER_NOT_EXIST);
        }
        existBuyerInfo.setPassword(newPassword);
        BuyerInfo save = buyerService.save(existBuyerInfo);
        if (save == null) {
            log.error("【买家端修改用户密码】发生异常{}", ResultEnum.BUYER_EDIT_FAIL.getMessage());
            throw new BuyException(ResultEnum.BUYER_EDIT_FAIL);
        }
        log.info("【买家端修改用户密码】{}", ResultEnum.BUYER_EDIT_SUCCESS);
        // 清除cookie和session
        String[] fields = {cookieConfig.getBuyerId(), cookieConfig.getBuyerName()};
        TokenUtils.cleanLoginTrace(fields, request, response);
        return ResultVOUtil.success();
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
        // 清除cookie和session
        String[] fields = {cookieConfig.getBuyerId(), cookieConfig.getBuyerName()};
        TokenUtils.cleanLoginTrace(fields, request, response);
        return ResultVOUtil.success();
    }

    @GetMapping("/findByBuyerId")
    public ResultVO findOne(String buyerId) {
        try {
            BuyerInfo buyerInfo = buyerService.findByBuyerId(buyerId);
            return ResultVOUtil.success(buyerInfo);
        } catch (SellException e) {
            return ResultVOUtil.error(200, e.getMessage());
        }
    }
}
