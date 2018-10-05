package com.xukai.netshop.controller;

import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/28 10:48
 * Modified By:
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 从第一页开始, size
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(page - 1, size));
        ModelAndView mav = new ModelAndView("sell/order/list");
        mav.addObject("orderDTOPage", orderDTOPage);
        mav.addObject("currentPage", page);
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
}
