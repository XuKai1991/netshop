package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.BuyerAddress;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.service.BuyerAddressService;
import com.xukai.netshop.utils.TokenUtils;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/31 11:04
 * @modified By:
 */
@RestController
@RequestMapping("/buyer/address")
@Slf4j
public class BuyerAddressController {

    @Autowired
    private BuyerAddressService buyerAddressService;

    @Autowired
    private CookieConfig cookieConfig;

    /**
     * 保存/修改买家常用地址
     *
     * @param buyerAddress
     * @param request
     * @return
     */
    @PostMapping("/save")
    public ResultVO save(@Valid BuyerAddress buyerAddress, HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【买家端保存常用地址】参数不正确, buyerAddress={}", buyerAddress);
            throw new BuyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isEmpty(buyerAddress.getBuyerId())) {
            String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
            if (StringUtils.isEmpty(buyerId)) {
                log.error("【买家端保存常用地址】buyerId为空");
                throw new BuyException(ResultEnum.PARAM_ERROR);
            }
            buyerAddress.setBuyerId(buyerId);
        }
        buyerAddressService.save(buyerAddress);
        log.info("【买家端保存常用地址】保存成功");
        return ResultVOUtil.success();
    }

    /**
     * 根据主键查找常用地址
     *
     * @param buyerAddressId
     * @return
     */
    @GetMapping("/findOne")
    public ResultVO findOne(String buyerAddressId) {
        if (StringUtils.isEmpty(buyerAddressId)) {
            log.error("【买家端查看常用地址】buyerAddressId为空");
            throw new BuyException(ResultEnum.PARAM_ERROR);
        }
        BuyerAddress buyerAddress = buyerAddressService.findOne(buyerAddressId);
        return ResultVOUtil.success(buyerAddress);
    }

    /**
     * 根据buyerId列出用户所有的常用地址
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(HttpServletRequest request) {
        String buyerId = TokenUtils.getToken(cookieConfig.getBuyerId(), request);
        if (StringUtils.isEmpty(buyerId)) {
            log.error("【买家端查看常用地址】buyerId为空");
            throw new BuyException(ResultEnum.PARAM_ERROR);
        }
        List<BuyerAddress> buyerAddressList = buyerAddressService.findByBuyerId(buyerId);
        return ResultVOUtil.success(buyerAddressList);
    }
}
