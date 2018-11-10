package com.xukai.netshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/11/6 12:27
 * @modified By:
 */
@Data
@ConfigurationProperties(prefix = "crawler")
@Component
public class CrawlerConfig {

    /**
     * 代理地址来源页
     */
    public String proxySources;

    /**
     * 代理地址来源页翻页上限
     */
    public Integer proxySourcesMaxPage;

    /**
     * 物流信息抓取BaseUrl
     */
    public String expressCrawlerBaseUrl;

    /**
     * 代理地址储存队列低阈值
     */
    public Integer proxyAddressQueueThreshold;

    /**
     * 可用代理列表最基础容量
     */
    public Integer activeProxyListBaseThreshold;

    /**
     * 可用代理列表最大容量
     */
    public Integer activeProxyListMaxThreshold;

    /**
     * 代理爬虫等待时间
     */
    public Integer proxyCrawlerWaitTime;

    /**
     * 快递爬虫中间暂停时间
     */
    public Integer expressCrawlerPauseWaitTime;

    /**
     * 快递爬虫强行停止时间
     */
    public Integer expressCrawlerForceStopWaitTime;
}
