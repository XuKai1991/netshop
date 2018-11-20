package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.dataobject.ExpressInfo;
import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.ExpressShipperEnum;
import com.xukai.netshop.enums.ExpressStatusEnum;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.ExpressService;
import com.xukai.netshop.service.OrderService;
import com.xukai.netshop.service.PayService;
import com.xukai.netshop.utils.EnumUtils;
import com.xukai.netshop.utils.ResultVOUtil;
import com.xukai.netshop.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

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

    @Autowired
    private ExpressService expressService;

    @Autowired
    private CookieConfig cookieConfig;

    @GetMapping("/list")
    public ModelAndView list(OrderMaster s_order, BigDecimal minAmount, BigDecimal maxAmount, HttpServletRequest request,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "8") Integer size) {
        String shopId = TokenUtils.getToken(cookieConfig.getShopId(), request);
        s_order.setShopId(shopId);
        Page<OrderDTO> orderDTOPage = orderService.findOnCondition(s_order, minAmount, maxAmount, new PageRequest(page - 1, size));
        ModelAndView mav = new ModelAndView("sell/order/list");
        Map<String, String> OrderStatusEnumMap = EnumUtils.listEnum(OrderStatusEnum.class);
        Map<String, String> expressShipperEnumMap = EnumUtils.listEnum(ExpressShipperEnum.class);
        mav.addObject("s_order", s_order);
        mav.addObject("orderDTOPage", orderDTOPage);
        mav.addObject("orderStatusMap", OrderStatusEnumMap);
        mav.addObject("expressShipperEnumMap", expressShipperEnumMap);
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
        mav.addObject("url", "/seller/order/list");
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
        Map<String, String> expressShipperEnumMap;
        try {
            orderDTO = orderService.findOne(orderId);
            expressShipperEnumMap = EnumUtils.listEnum(ExpressShipperEnum.class);
        } catch (SellException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/order/list");
            mav.setViewName("sell/common/error");
            return mav;
        }
        mav.addObject("orderDTO", orderDTO);
        mav.addObject("expressShipperEnumMap", expressShipperEnumMap);
        mav.setViewName("sell/order/detail");
        return mav;
    }

    /**
     * 订单发货
     *
     * @param expressInfo
     * @return
     */
    @PostMapping("/send")
    public ResultVO send(ExpressInfo expressInfo) {
        try {
            expressInfo.setExpressStatus(ExpressStatusEnum.IN_TRANSIT.getCode());
            orderService.send(expressInfo.getOrderId());
            expressService.save(expressInfo);
        } catch (SellException e) {
            log.error("【卖家端订单发货】发生异常{}", e);
            return ResultVOUtil.error(e.getCode(), e.getMessage());
        }
        return ResultVOUtil.success();
    }

    /**
     * 卖家端删除订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("orderId") String orderId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", "/seller/order/list");
        try {
            // 删除订单信息
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

    /**
     * 卖家端修改订单价格
     *
     * @param orderId
     * @param amount
     * @param actualAmount
     * @return
     */
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

    @Autowired
    private PayService payService;

    /**
     * 订单付款
     *
     * @param orderId
     * @return
     */
    @PostMapping("/pay")
    public ResultVO pay(@RequestParam("orderId") String orderId) {
        try {
            payService.pay(orderId);
        } catch (BuyException e) {
            log.error("【卖家端修改订单为已支付】订单状态不正确");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        return ResultVOUtil.success();
    }

    /**
     * 获取快递枚举列表
     *
     * @return
     */
    @GetMapping("/listExpress")
    public ResultVO listStatus() {
        Map<String, String> expressEnumMap = EnumUtils.listEnum(ExpressShipperEnum.class);
        return ResultVOUtil.success(expressEnumMap);
    }
}
