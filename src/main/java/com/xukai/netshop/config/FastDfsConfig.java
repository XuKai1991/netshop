package com.xukai.netshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Xukai
 * @description: 图片上传功能配置
 * @createDate: 2018/11/9 17:31
 * @modified By:
 */
@Data
@ConfigurationProperties(prefix = "fastdfs")
@Component
public class FastDfsConfig {

    /**
     * 图片服务器图片路径保存副本地址
     */
    public String psicSaveLogUrl;

}
