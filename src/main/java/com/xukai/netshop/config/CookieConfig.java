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
     * token标识
     */
    public String token;

    /**
     * 超时时间
     */
    public Integer expire;

}
