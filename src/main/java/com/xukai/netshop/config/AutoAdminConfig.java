package com.xukai.netshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Xukai
 * @description: 系统自动功能配置参数
 * @createDate: 2018/11/9 17:31
 * @modified By:
 */
@Data
@ConfigurationProperties(prefix = "autoAdmin")
@Component
public class AutoAdminConfig {

    /**
     * 自动确认收货时长
     */
    public Integer autoConfirmReceiveGoodsWaitTime;

    /**
     * 未支付订单自动取消时长
     */
    public Integer autoCancelNotPayedOrderWaitTime;
}
