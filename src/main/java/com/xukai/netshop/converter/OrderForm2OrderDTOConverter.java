package com.xukai.netshop.converter;

import com.xukai.netshop.dataobject.OrderDetail;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.form.OrderForm;
import com.xukai.netshop.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/24 20:23
 * Modified By:
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerId(orderForm.getBuyerId());
        orderDTO.setBuyerAddress(orderForm.getBuyerAddress());
        orderDTO.setBuyerName(orderForm.getBuyerName());
        orderDTO.setBuyerPhone(orderForm.getBuyerPhone());
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = JsonUtils.jsonToList(orderForm.getItems(), OrderDetail.class);
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
