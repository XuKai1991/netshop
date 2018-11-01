package com.xukai.netshop.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xukai.netshop.utils.serializer.Fen2YuanSerializer;
import lombok.Data;

import java.net.URI;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/27 10:27
 * Modified By:
 */
@Data
public class PayVO {

    private String prePayParams;

    private URI payUri;

    private String appId;

    private String timeStamp;

    private String nonceStr;

    /**
     * 由于package为java保留关键字，因此改为packageValue.
     */
    @JsonProperty("package")
    private String packageValue;

    private String signType;

    private String paySign;

    //交易类型
    private String tradeType;

    //订单金额，订单总金额，单位为分，只能为整数，详见支付金额
    @JsonSerialize(using = Fen2YuanSerializer.class)
    private Integer totalFee;

    //商户订单号，商户侧传给微信的订单号
    private String outTradeNo;

    //微信支付订单号，微信生成的订单号，在支付通知中有返回
    private String transactionId;

    //商户退款单号，商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
    private String outRefundNo;

    //退款金额，退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
    @JsonSerialize(using = Fen2YuanSerializer.class)
    private Integer refundFee;

    //微信退款单号.
    private String refundId;

    //异步通知回调地址，通知URL必须为外网可访问的url，不允许带参数
    private String notifyUrl;

}
