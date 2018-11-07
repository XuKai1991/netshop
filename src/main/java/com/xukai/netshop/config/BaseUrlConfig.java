package com.xukai.netshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/4 20:20
 * @modified By:
 */
@Data
@ConfigurationProperties(prefix = "baseUrl")
@Component
public class BaseUrlConfig {

    /**
     * 前端基础URL
     */
    public String frontBaseUrl;

    /**
     * 后端基础URL
     */
    public String backBaseUrl;

    /**
     * 图片服务器URL
     */
    public String imageServerUrl;
}
