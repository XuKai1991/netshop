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

    PRODUCT_NOT_EXIST(0, "商品不存在"),
    PRODUCT_NOT_ENOUGH(1, "商品库存不足"),
    ORDER_NOT_EXIST(3, "订单不存在"),
    ORDERDETAIL_NOT_EXIST(4, "订单详情不存在"),
    ORDER_STATUS_ERROR(5, "订单状态错误"),
    ORDER_UPDATE_FAIL(6, "订单更新失败"),
    ORDER_DETAIL_EMPTY(7, "订单无商品详情"),
    PAY_STATUS_ERROR(8, "支付状态错误"),
    CART_EMPTY(9, "购物车为空"),
    ORDER_OWNER_ERROR(10, "订单操作权限错误"),
    WX_MP_ERROR(11, "微信公众账号权限错误"),
    WX_PAY_ERROR(12, "微信支付错误"),
    WX_PAY_NOTIFY_MONEY_VERIFY_ERROR(13, "微信支付异步通知金额校验错误"),
    ORDER_CANCEL_SUCCESS(14, "订单取消成功"),
    ORDER_FINISH_SUCCESS(15, "订单完结成功"),
    ORDER_DELETE_SUCCESS(16, "订单删除成功"),
    PRODUCT_STATUS_ERROR(17, "商品状态错误"),
    PRODUCT_OFFSALE_SUCCESS(18, "商品下架成功"),
    PRODUCT_ONSALE_SUCCESS(19, "商品上架成功"),
    SELLER_NOT_EXIST(20, "卖家信息不存在"),
    LOGIN_FAIL(21, "登录失败，登录信息不正确"),
    LOGOUT_SUCCESS(22, "注销成功"),

    BUYER_REGISTER_SUCCESS(23, "用户注册成功"),
    BUYER_REGISTER_FAIL(24, "用户注册失败"),
    BUYER_LOGIN_FAIL(25, "用户登录失败"),
    CART_NOT_EXIST(26, "购物车不存在"),
    ORDER_SUCCESS_MSG(27, "下单成功，卖家会尽管安排发货，如有需要请微信联系卖家"),
    PICTURE_EMPTY(28, "上传图片为空"),
    PICTURE_UPLOAD_FAIL(29, "图片上传失败"),
    CONTENT_EMPTY(30, "内容为空"),
    PICTURE_DELETE_FAIL(31, "图片删除失败"),
    ORDER_EDIT_SUCCESS(32, "订单修改成功"),
    ORDER_EDIT_FAIL(33, "订单修改成功"),
    LOGIN_STATUS_ERROR(34, "用户未登录"),
    CATEGORY_NOT_EXIST(35, "商品类目不存在"),

    PARAM_ERROR(100, "参数不正确");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
