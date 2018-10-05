package com.xukai.netshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.converter.OrderForm2OrderDTOConverter;
import com.xukai.netshop.dto.CartDTO;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.form.OrderForm;
import com.xukai.netshop.service.BuyerCartService;
import com.xukai.netshop.service.OrderService;
import com.xukai.netshop.utils.CookieUtils;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 17:30
 * Modified By:
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerCartService buyerCartService;

    @Autowired
    private CookieConfig cookieConfig;

    /**
     * 创建订单
     *
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new BuyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new BuyException(ResultEnum.CART_EMPTY);
        }
        // 创建订单
        OrderDTO createResult = orderService.create(orderDTO);
        // 删除购物车中已经被下单的商品
        List<CartDTO> cartDTOList = JSONObject.parseArray(orderForm.getItems(), CartDTO.class);
        StringBuffer itemIdsBf = new StringBuffer();
        for (CartDTO cartDTO : cartDTOList) {
            if (StringUtils.isNotEmpty(cartDTO.getItemId())) {
                itemIdsBf.append("_" + cartDTO.getItemId());
            }
        }
        buyerCartService.deleteItems(itemIdsBf.toString().replaceFirst("_", ""), orderForm.getBuyerId());
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        map.put("orderAmount", createResult.getOrderAmount().toString());
        map.put("msg", ResultEnum.ORDER_SUCCESS_MSG.getMessage());
        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     *
     * @param request
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(HttpServletRequest request,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        String buyerId = CookieUtils.get(cookieConfig.getBuyerId(), request).getValue();
        if (StringUtils.isEmpty(buyerId)) {
            log.error("【查询订单列表】buyerId为空");
            throw new BuyException(ResultEnum.PARAM_ERROR);
        }
        Page<OrderDTO> orderDTOPage = orderService.findList(buyerId, new PageRequest(page, size));
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】buyerId或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderService.findOne(orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     *
     * @param request
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ResultVO cancel(HttpServletRequest request, @RequestParam("orderId") String orderId) {
        String buyerId = CookieUtils.get(cookieConfig.getBuyerId(), request).getValue();
        if (StringUtils.isEmpty(buyerId) || StringUtils.isEmpty(orderId)) {
            log.error("【取消订单】buyerId或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderService.findAndCheckOrderOne(orderId, buyerId);
        orderService.cancel(orderId);
        return ResultVOUtil.success();
    }

    /**
     * 买家删除订单
     *
     * @param request
     * @param orderId
     * @return
     */
    @GetMapping("/delete")
    public ResultVO delete(HttpServletRequest request, @RequestParam("orderId") String orderId) {
        String buyerId = CookieUtils.get(cookieConfig.getBuyerId(), request).getValue();
        if (StringUtils.isEmpty(buyerId) || StringUtils.isEmpty(orderId)) {
            log.error("【删除订单】buyerId或orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderService.findAndCheckOrderOne(orderId, buyerId);
        orderService.buyerDelete(orderId);
        return ResultVOUtil.success();
    }
}
