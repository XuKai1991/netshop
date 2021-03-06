package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.CartDetail;
import com.xukai.netshop.dataobject.OrderDetail;
import com.xukai.netshop.dataobject.OrderMaster;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.dto.OrderDTO;
import com.xukai.netshop.enums.OrderStatusEnum;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.repository.OrderDetailRepository;
import com.xukai.netshop.repository.OrderMasterRepository;
import com.xukai.netshop.repository.ProductInfoRepository;
import com.xukai.netshop.service.ExpressService;
import com.xukai.netshop.service.OrderService;
import com.xukai.netshop.service.ProductService;
import com.xukai.netshop.controller.WebSocket;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 11:50
 * Modified By:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ExpressService expressService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtils.genUniqueKey();
        // 查询商品（数量, 价格）
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<OrderDetail> toDelete = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            // 检查商品是否在售
            if (!productInfo.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
                toDelete.add(orderDetail);
                continue;
            }
            // 计算订单总价
            orderAmount = productInfo.getProductPrice().
                    multiply(BigDecimal.valueOf(orderDetail.getProductQuantity())).
                    add(orderAmount);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtils.genUniqueKey());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductPurchasePrice(productInfo.getProductPurchasePrice());
            orderDetail.setProductImgMd(productInfo.getProductImgMd());
            orderDetail.setProductName(productInfo.getProductName());
            orderDetailRepository.save(orderDetail);
        }
        orderDetailList.removeAll(toDelete);
        // 订单写入数据库
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderActualAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NOT_PAY.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);
        // 扣库存
        List<CartDetail> cartDetailList = orderDetailList.stream()
                .map(e -> new CartDetail(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDetailList);
        // 发送websocket消息
        webSocket.sendMessage(orderDTO.getShopId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            log.error("【查询订单】订单不存在, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【查询订单详情】订单详情不存在, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        // 更新订单详情中商品名称和图片，但如果商品已经被删除，就不做处理保持原状
        refreshOrderDetailList(orderDetailList);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findOnCondition(OrderMaster s_order, BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable) {
        if (minAmount != null && maxAmount != null && minAmount.compareTo(maxAmount) == 1) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByShopIdLikeAndOrderStatusLikeAndOrderIdLikeAndBuyerIdLikeAndBuyerNameLikeAndBuyerAddressLikeAndBuyerPhoneLikeAndOrderAmountBetweenOrderByCreateTimeDesc(
                StringUtils.isEmpty(s_order.getShopId()) ? "%%" : s_order.getShopId(),
                StringUtils.isEmpty(s_order.getOrderStatus()) ? "%%" : s_order.getOrderStatus(),
                "%" + (StringUtils.isEmpty(s_order.getOrderId()) ? "" : s_order.getOrderId()) + "%",
                "%" + (StringUtils.isEmpty(s_order.getBuyerId()) ? "" : s_order.getBuyerId()) + "%",
                "%" + (StringUtils.isEmpty(s_order.getBuyerName()) ? "" : s_order.getBuyerName()) + "%",
                "%" + (StringUtils.isEmpty(s_order.getBuyerAddress()) ? "" : s_order.getBuyerAddress()) + "%",
                "%" + (StringUtils.isEmpty(s_order.getBuyerPhone()) ? "" : s_order.getBuyerPhone()) + "%",
                minAmount == null ? new BigDecimal("0") : minAmount,
                maxAmount == null ? new BigDecimal("99999") : maxAmount,
                pageable
        );
        // 返回订单列表可携带订单详情数据
        // 更新订单详情中商品名称和图片，但如果商品已经被删除，就不做处理保持原状
        List<OrderDTO> orderDTOList = orderMasterPage.getContent().stream().map(e -> {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(e.getOrderId());
            if (!CollectionUtils.isEmpty(orderDetailList)) {
                refreshOrderDetailList(orderDetailList);
            }
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e, orderDTO);
            orderDTO.setOrderDetailList(orderDetailList);
            return orderDTO;
        }).collect(Collectors.toList());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = SellException.class)
    public void cancel(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NOT_PAY.getCode()) && !orderMaster.getOrderStatus().equals(OrderStatusEnum.HAS_PAY.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        Boolean hasPayFlag = orderMaster.getOrderStatus().equals(OrderStatusEnum.HAS_PAY.getCode()) ? true : false;
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderId={}", orderMaster.getOrderId());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // TODO
        // 如果取消已付款的订单，需要退款
        if (hasPayFlag) {

            // orderMaster.setOrderStatus(OrderStatusEnum.IN_REFUND.getCode());
            orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());

        }
        // 返回库存
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【取消订单】订单中无商品详情, orderId={}", orderMaster.getOrderId());
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDetail> cartDetailList = orderDetailList.stream()
                .map(e -> new CartDetail(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDetailList);
    }

    @Override
    @Transactional(rollbackFor = SellException.class)
    public void send(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.HAS_PAY.getCode())) {
            log.error("【订单发货】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.HAS_SEND.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单发货】更新失败, orderId={}", orderMaster.getOrderId());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
    }

    @Override
    public void receive(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.HAS_SEND.getCode())) {
            log.error("【订单收货】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new BuyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.HAS_RECEIVE.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单收货】更新失败, orderId={}", orderMaster.getOrderId());
            throw new BuyException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 修改物流状态
        expressService.receive(orderId);
    }

    @Override
    public void findAndCheckOrderOne(String orderId, String buyerId) {
        OrderDTO orderDTO = findOne(orderId);
        //判断是否是自己的订单
        if (!orderDTO.getBuyerId().equals(buyerId)) {
            log.error("【查询订单】订单操作权限错误. buyerId={}, orderDTO={}", buyerId, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
    }

    @Override
    public void buyerDelete(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode()) && !orderMaster.getOrderStatus().equals(OrderStatusEnum.HAS_RECEIVE.getCode())) {
            log.error("【买家删除订单】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new BuyException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.BUYER_DEL.getCode());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【买家删除订单】更新失败, orderId={}", orderMaster.getOrderId());
            throw new BuyException(ResultEnum.ORDER_STATUS_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = SellException.class)
    public void sellerDelete(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.CANCEL.getCode()) && !orderMaster.getOrderStatus().equals(OrderStatusEnum.HAS_RECEIVE.getCode()) && !orderMaster.getOrderStatus().equals(OrderStatusEnum.BUYER_DEL.getCode())) {
            log.error("【卖家删除订单】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 先删除订单详情
        int delOrderDetailResult = orderDetailRepository.deleteByOrderId(orderId);
        if (delOrderDetailResult == 0) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        // 再删除订单
        int delOrderResult = orderMasterRepository.deleteByOrderId(orderId);
        if (delOrderResult == 0) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 再删除订单物流信息（不一定有）
        expressService.deleteByOrderId(orderId);
    }

    @Override
    public void editActualAmount(String orderId, String amount, String actualAmount) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            log.error("【卖家修改订单】订单不存在, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NOT_PAY.getCode())) {
            log.error("【卖家修改订单】订单状态不正确, orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断应付价格是否就是页面显示的价格，防止伪造
        if (orderMaster.getOrderAmount().compareTo(new BigDecimal(amount)) != 0) {
            log.error("【卖家修改订单】订单数据不匹配，实际应支付金额为{}，页面参数为{}", orderMaster.getOrderAmount(), amount);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderMaster.setOrderActualAmount(new BigDecimal(actualAmount));
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【卖家修改订单】修改失败, orderId={}", orderMaster.getOrderId());
            throw new SellException(ResultEnum.ORDER_EDIT_FAIL);
        }
    }

    /**
     * 更新订单详情中商品名称和图片，但如果商品已经被删除，就不做处理保持原状
     *
     * @param orderDetailList
     */
    void refreshOrderDetailList(List<OrderDetail> orderDetailList) {
        ProductInfo productInfo;
        for (OrderDetail orderDetail : orderDetailList) {
            productInfo = productInfoRepository.findOne(orderDetail.getProductId());
            if (productInfo != null) {
                orderDetail.setProductName(productInfo.getProductName());
                orderDetail.setProductImgMd(productInfo.getProductImgMd());
            }
        }
    }
}
