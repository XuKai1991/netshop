package com.xukai.netshop.scheduledTasks;

import com.xukai.netshop.config.CrawlerConfig;
import com.xukai.netshop.dataobject.ExpressInfo;
import com.xukai.netshop.service.ExpressService;
import com.xukai.netshop.utils.CrawlerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.List;

import static com.xukai.netshop.constant.CrawlerCons.*;
import static com.xukai.netshop.utils.CrawlerUtils.addToProxySourceQueue;

/**
 * @author: Xukai
 * @description: 物流详情自动更新
 * @createDate: 2018/11/6 01:47
 * @modified By:
 */
@Configuration
@Slf4j
public class ExpressTask {

    @Autowired
    private CrawlerConfig crawlerConfig;

    @Autowired
    private ExpressService expressService;

    /**
     * 每2小时执行一次(15分钟时)
     */
    // @Scheduled(cron = "15 */2 * * * ?")
    @Scheduled(cron = "0 15 */2 * * ?")
    public void refreshLogisticsDetail() {
        long startTime = System.currentTimeMillis();
        List<ExpressInfo> expressInTransitList = expressService.listInTransit();
        // 执行标志
        Boolean exeExpressFlag = true;
        while (exeExpressFlag) {
            // 运输中的快递列表不为空 且 代理地址队列不为空 则执行
            if (!expressInTransitList.isEmpty() && !PROXY_ADDRESS_QUEUE.isEmpty()) {
                // log.info("活动线程数量为：" + String.valueOf(EXPRESS_CRAWLER_THREAD_EXECUTOR.getActiveCount()));
                EXPRESS_CRAWLER_THREAD_EXECUTOR.execute(() -> {
                    log.info("目前可用代理数量为：{}", ACTIVE_PROXY_ADDRESS_LIST.size());
                    ExpressInfo expressInfo;
                    synchronized (expressInTransitList) {
                        expressInfo = expressInTransitList.get(0);
                        expressInTransitList.remove(0);
                    }
                    // log.info("尝试获取订单{}物流信息", expressInfo.getOrderId());
                    String[] expressCrawlerBaseUrl = crawlerConfig.getExpressCrawlerBaseUrl().split("\\|");
                    String expressCrawlerUrl = expressCrawlerBaseUrl[0] + expressInfo.getExpressShipper() + expressCrawlerBaseUrl[1] + expressInfo.getExpressNumber();
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(expressCrawlerUrl);
                    // 获取高匿IP代理
                    String proxyStr = getFromActiveProxyList();
                    String proxyIp = proxyStr.split(":")[0];
                    Integer proxyPort = Integer.valueOf(proxyStr.split(":")[1]);
                    HttpHost proxy = new HttpHost(proxyIp, proxyPort);
                    RequestConfig config = RequestConfig.custom()
                            .setConnectionRequestTimeout(3000)
                            .setSocketTimeout(3000)
                            .setProxy(proxy)
                            .build();
                    httpGet.setConfig(config);
                    httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0");
                    CloseableHttpResponse response = null;
                    try {
                        // 实际情况中执行http get请求在某些情况下，如果连接超时会在内部方法进行try/catch
                        // 此时返回的response为null，因此如果在此处判断response是否null，null也代表连接超时
                        // 如果请求出现异常，将ExpressInfo对象重新添加回列表
                        try {
                            response = httpClient.execute(httpGet);
                        } catch (Exception e) {
                            expressInTransitList.add(expressInfo);
                            // 删除无效代理
                            deleteFromActiveProxyList(proxyStr);
                            throw new Exception();
                        }
                        if (response != null) {
                            HttpEntity entity = response.getEntity();
                            //根据指定编码获取网页内容，一般网站的编码都是utf-8，也有是gb2312
                            String webContent = EntityUtils.toString(entity, "utf-8");
                            if (webContent.startsWith("{\"message\":\"ok")) {
                                // 将物流信息保存到数据库
                                expressInfo.setLogisticsDetail(webContent);
                                expressService.save(expressInfo);
                                log.info("【物流信息更新】订单{}物流更新成功", expressInfo.getOrderId());
                            } else {
                                throw new Exception();
                            }
                        } else {
                            expressInTransitList.add(expressInfo);
                            // 删除无效代理
                            deleteFromActiveProxyList(proxyStr);
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        log.error("【物流信息更新】订单{}：更新请求失败或超时", expressInfo.getOrderId());
                    } finally {
                        try {
                            if (response != null) {
                                response.close();
                            }
                            httpClient.close();
                        } catch (Exception e) {
                            log.error("【物流信息更新】订单{}：更新关闭连接", expressInfo.getOrderId());
                        }
                    }
                });
            } else {
                if (PROXY_ADDRESS_QUEUE.size() < crawlerConfig.getProxyAddressQueueThreshold()) {
                    // 重新抓取代理
                    EXPRESS_CRAWLER_THREAD_EXECUTOR.execute(() -> crawlProxy());
                    log.info("【物流信息更新】重新抓取代理");
                }
                // 判断待更新物流列表是否为空 且 线程池中是否还有正在工作的线程
                if (expressInTransitList.isEmpty() && EXPRESS_CRAWLER_THREAD_EXECUTOR.getActiveCount() == 0) {
                    exeExpressFlag = false;
                    log.info("【物流信息更新】任务结束");
                }
            }
            // 如果更新任务已经超时，强行停止
            if (System.currentTimeMillis() - startTime > crawlerConfig.getExpressCrawlerForceStopWaitTime()) {
                exeExpressFlag = false;
                log.info("【物流信息更新】任务强行结束");
            }
            try {
                // 休眠，避免被快递100封IP
                Thread.sleep(crawlerConfig.getExpressCrawlerPauseWaitTime());
            } catch (InterruptedException e) {
                log.error("【物流信息更新】线程休眠错误");
            }
        }
    }

    /**
     * 从可用代理列表中获取代理
     *
     * @return
     */
    public String getFromActiveProxyList() {
        if (ACTIVE_PROXY_ADDRESS_LIST.size() < crawlerConfig.getActiveProxyListBaseThreshold()) {
            while (ACTIVE_PROXY_ADDRESS_LIST.size() <= crawlerConfig.getActiveProxyListMaxThreshold() && !PROXY_ADDRESS_QUEUE.isEmpty()) {
                ACTIVE_PROXY_ADDRESS_LIST.add(PROXY_ADDRESS_QUEUE.poll());
            }
        }
        int index = RANDOM.nextInt(ACTIVE_PROXY_ADDRESS_LIST.size());
        // log.info("获取代理：" + ACTIVE_PROXY_ADDRESS_LIST.get(index));
        return ACTIVE_PROXY_ADDRESS_LIST.get(index);
    }

    /**
     * 从可用代理列表删除无效代理
     *
     * @param proxy
     */
    public void deleteFromActiveProxyList(String proxy) {
        ACTIVE_PROXY_ADDRESS_LIST.remove(proxy);
        // log.info("删除无效代理：" + proxy);
    }


    /**
     * 每3小时执行一次(45分钟时)
     */
    @Scheduled(cron = "0 45 */3 * * ?")
    public void crawlProxy() {
        log.info("目前来源页队列有：");
        if (!PROXY_SOURCES_QUEUE.isEmpty()) {
            for (String source : PROXY_SOURCES_QUEUE) {
                log.info(source);
            }
        } else {
            log.info("0");
        }
        log.info("目前代理总队列数量为：{}", PROXY_ADDRESS_QUEUE.size());
        while (PROXY_ADDRESS_QUEUE.size() < crawlerConfig.getProxyAddressQueueThreshold()) {
            // 如果代理来源页队列为空，添加来源页
            if (PROXY_SOURCES_QUEUE.isEmpty()) {
                String[] proxyList = crawlerConfig.getProxySources().split("\\|");
                for (int i = 1; i <= crawlerConfig.getProxySourcesMaxPage(); i++) {
                    for (String baseProxy : proxyList) {
                        PROXY_SOURCES_QUEUE.add(baseProxy + i);
                    }
                }
            }
            String url = PROXY_SOURCES_QUEUE.poll();
            if (StringUtils.isEmpty(url)) {
                return;
            }
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .build();
            httpGet.setConfig(config);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0");
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
                if (response != null) {
                    HttpEntity entity = response.getEntity();
                    String webContent;
                    webContent = EntityUtils.toString(entity, "utf-8");
                    CrawlerUtils.getHostAndPort(webContent);
                    log.info("【抓取代理】任务成功");
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                log.error("【抓取代理】请求{}失败或超时", url);
                addToProxySourceQueue(url, "由于异常");
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    log.error("【抓取代理】关闭请求{}连接", url);
                }
            }
            try {
                // 休眠，避免被西刺封IP
                Thread.sleep(crawlerConfig.getProxyCrawlerWaitTime());
            } catch (InterruptedException e) {
                log.error("【抓取代理】线程休眠错误");
            }
        }
    }
}
