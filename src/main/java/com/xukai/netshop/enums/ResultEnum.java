package com.xukai.netshop.enums;

import lombok.Getter;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 14:27
 * Modified By:
 */
@Getter
public enum ResultEnum {

    // 买家用户类 1**
    BUYER_REGISTER_SUCCESS(101, "用户注册成功"),
    BUYER_REGISTER_FAIL(102, "用户注册失败"),
    BUYER_LOGIN_FAIL(103, "用户登录失败"),
    BUYER_NOT_EXIST(104, "买家信息不存在"),
    BUYER_EDIT_FAIL(105, "用户修改失败"),
    BUYER_EDIT_SUCCESS(106, "用户修改成功"),
    BUYER_NOT_LOGIN(107, "用户未登录"),
    BUYER_DELETE_SUCCESS(108, "买家用户删除成功"),
    BUYER_ADDRESS_SAVE_FAIL(109, "买家保存常用地址失败"),
    BUYER_ADDRESS_NOT_EXIST(110, "买家常用地址不存在"),

    // 订单状态 2**
    ORDER_CANCEL_SUCCESS(201, "订单取消成功"),
    ORDER_SEND_SUCCESS(202, "订单发货成功"),
    ORDER_RECEIVE_SUCCESS(203, "订单收货成功"),
    ORDER_DELETE_SUCCESS(204, "订单删除成功"),

    // 商品状态类 3**
    PRODUCT_STATUS_ERROR(301, "商品状态错误"),
    PRODUCT_OFF_SALE_SUCCESS(302, "商品下架成功"),
    PRODUCT_ON_SALE_SUCCESS(303, "商品上架成功"),

    // 购物车类 4**
    CART_NOT_EXIST(401, "购物车不存在"),
    CART_EMPTY(402, "购物车为空"),

    // 物流快递类 5**
    EXPRESS_INFO_SAVE_FAIL(501, "物流信息保存失败"),
    EXPRESS_INFO_DELETE_FAIL(502, "物流信息保存失败"),
    EXPRESS_INFO_NOT_EXIST(502, "物流信息不存在"),

    SELLER_NOT_EXIST(20, "卖家信息不存在"),
    LOGIN_FAIL(21, "登录失败，登录信息不正确"),
    LOGOUT_SUCCESS(22, "注销成功"),
    PRODUCT_NOT_EXIST(0, "商品不存在"),
    PRODUCT_NOT_ENOUGH(1, "商品库存不足"),
    ORDER_NOT_EXIST(3, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(4, "订单详情不存在"),
    ORDER_STATUS_ERROR(5, "订单状态错误"),
    ORDER_UPDATE_FAIL(6, "订单更新失败"),
    ORDER_DETAIL_EMPTY(7, "订单无商品详情"),
    PAY_STATUS_ERROR(8, "支付状态错误"),
    ORDER_OWNER_ERROR(10, "订单操作权限错误"),
    WX_MP_ERROR(11, "微信公众账号权限错误"),
    WX_PAY_ERROR(12, "微信支付错误"),
    WX_PAY_NOTIFY_MONEY_VERIFY_ERROR(13, "微信支付异步通知金额校验错误"),
    ORDER_SUCCESS_MSG(27, "下单成功，卖家会尽管安排发货，如有需要请微信联系卖家"),
    PICTURE_EMPTY(28, "上传图片为空"),
    PICTURE_UPLOAD_FAIL(29, "图片上传失败"),
    CONTENT_EMPTY(30, "内容为空"),
    PICTURE_DELETE_FAIL(31, "图片删除失败"),
    ORDER_EDIT_SUCCESS(32, "订单修改成功"),
    ORDER_EDIT_FAIL(33, "订单修改成功"),
    LOGIN_STATUS_ERROR(34, "用户未登录"),
    CATEGORY_NOT_EXIST(35, "商品类目不存在"),
    CATEGORY_DELETE_SUCCESS(36, "商品类目删除成功"),
    CATEGORY_DELETE_FAIL(37, "商品类目删除失败"),
    EMAIL_NOT_EXIST(38, "邮箱不存在"),
    GET_BACK_PSD_FAIL(39, "找回密码失败"),

    PARAM_ERROR(100, "参数不正确");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
