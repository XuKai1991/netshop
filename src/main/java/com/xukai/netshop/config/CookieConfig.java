package com.xukai.netshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Xukai
 * @description: redis自定义常量
 * @createDate: 2018/7/10 10:00
 * @modified By:
 */
@Data
@ConfigurationProperties(prefix = "cookieConfig")
@Component
public class CookieConfig {

    /**
     * buyerId标识
     */
    public String buyerId;

    /**
     * buyerName标识
     */
    public String buyerName;

    /**
     * sellerId标识
     */
    public String sellerId;

    /**
     * 超时时间
     */
    public Integer expire;

}
