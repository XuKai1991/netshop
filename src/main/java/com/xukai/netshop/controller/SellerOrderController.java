package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.OrderService;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/28 10:48
 * Modified By:
 */
@RestController
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(OrderMaster s_order, BigDecimal minAmount, BigDecimal maxAmount,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<OrderDTO> orderDTOPage = orderService.findOnCondition(s_order, minAmount, maxAmount, new PageRequest(page - 1, size));
        ModelAndView mav = new ModelAndView("sell/order/list");
        mav.addObject("s_order", s_order);
        mav.addObject("orderDTOPage", orderDTOPage);
        mav.addObject("currentPage", page);
        mav.addObject("minAmount", minAmount);
        mav.addObject("maxAmount", maxAmount);
        mav.addObject("size", size);
        return mav;
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/netshop/seller/order/list");
        try {
            orderService.cancel(orderId);
        } catch (SellException e) {
            log.error("【卖家端取消订单】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }

    /**
     * 查看订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId) {
        ModelAndView mav = new ModelAndView();
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/netshop/seller/order/list");
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("orderDTO", orderDTO);
        mav.setViewName("sell/order/detail");
        return mav;
    }

    /**
     * 完结订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/netshop/seller/order/list");
        try {
            orderService.finish(orderId);
        } catch (SellException e) {
            log.error("【卖家端完结订单】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }

    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("orderId") String orderId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/netshop/seller/order/list");
        try {
            orderService.sellerDelete(orderId);
        } catch (SellException e) {
            log.error("【卖家端删除订单】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("msg", ResultEnum.ORDER_DELETE_SUCCESS.getMessage());
        mav.setViewName("sell/common/success");
        return mav;
    }

    @PostMapping("/edit")
    public ResultVO editActualAmount(String orderId, String amount, String actualAmount) {
        try {
            orderService.editActualAmount(orderId, amount, actualAmount);
        } catch (SellException e) {
            log.error("【卖家端修改订单】发生异常{}", e);
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
        return ResultVOUtil.success();
    }
}
