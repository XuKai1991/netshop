package com.xukai.netshop.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Xukai
 * @description: 配置session过期时间
 * @createDate: 2018/10/16 10:46
 * @modified By:
 */
@Configuration
public class SessionConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                // 单位为s
                container.setSessionTimeout(3600);
            }
        };
    }
}
