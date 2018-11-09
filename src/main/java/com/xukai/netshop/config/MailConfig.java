package com.xukai.netshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Xukai
 * @description: email配置参数
 * @createDate: 2018/11/9 17:31
 * @modified By:
 */
@Data
@ConfigurationProperties(prefix = "mail")
@Component
public class MailConfig {

    /**
     * 发送邮件端
     */
    public String sender;

}
