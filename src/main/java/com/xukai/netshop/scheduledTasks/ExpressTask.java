package com.xukai.netshop.scheduledTasks;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xukai.netshop.config.CrawlerConfig;
import com.xukai.netshop.dataobject.ExpressInfo;
import com.xukai.netshop.service.ExpressService;
import com.xukai.netshop.utils.CrawlerUtils;
import com.xukai.netshop.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
     * 每1小时执行一次(10分钟时)
     */
    @Scheduled(cron = "0 10 */1 * * ?")
    // @Scheduled(cron = "15 */1 * * * ?")
    public void refreshLogisticsDetail() {
        List<ExpressInfo> expressInTransitList = expressService.listInTransit();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("refreshLogisticsDetail-task-" + DateUtils.getNowTime()).build();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                10,
                10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        // 执行标志
        Boolean exeExpressFlag = true;
        while (exeExpressFlag) {
            // 运输中的快递列表不为空且代理地址队列不为空则执行
            if (!expressInTransitList.isEmpty() && !PROXY_ADDRESS_QUEUE.isEmpty()) {
                executorService.execute(() -> {
                    // log.info("目前可用代理数量为：{}", ACTIVE_PROXY_ADDRESS_LIST.size());
                    // 获取高匿IP代理
                    String proxyStr = getFromActiveProxyList();
                    String proxyIp = proxyStr.split(":")[0];
                    Integer proxyPort = Integer.valueOf(proxyStr.split(":")[1]);
                    ExpressInfo expressInfo;
                    synchronized (expressInTransitList) {
                        expressInfo = expressInTransitList.get(0);
                        expressInTransitList.remove(0);
                    }
                    String[] expressCrawlerBaseUrl = crawlerConfig.getExpressCrawlerBaseUrl().split("\\|");
                    String expressCrawlerUrl = expressCrawlerBaseUrl[0] + expressInfo.getExpressShipper() + expressCrawlerBaseUrl[1] + expressInfo.getExpressNumber();
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpGet httpGet = new HttpGet(expressCrawlerUrl);
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
                        response = httpClient.execute(httpGet);
                        if (response != null) {
                            HttpEntity entity = response.getEntity();
                            //根据指定编码获取网页内容，一般网站的编码都是utf-8，也有是gb2312
                            String webContent = EntityUtils.toString(entity, "utf-8");
                            if (webContent.startsWith("{\"message\"")) {
                                // 将物流信息保存到数据库
                                expressInfo.setLogisticsDetail(webContent);
                                expressService.save(expressInfo);
                                // 将代理重新放回队列，供下次使用
                                returnToActiveProxyList(proxyStr);
                            } else {
                                throw new Exception();
                            }
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        log.error("Exception" + e);
                        // 删除无效代理
                        deleteFromActiveProxyList(proxyStr);
                        expressInTransitList.add(expressInfo);
                    } finally {
                        try {
                            if (response != null) {
                                response.close();
                            }
                            httpClient.close();
                        } catch (Exception e) {
                            log.error("IOException" + e);
                        }
                    }
                });
                try {
                    // 休眠5秒，避免被快递100封IP
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("快递物流信息更新任务线程休眠报错", e);
                }
            } else {
                if (PROXY_ADDRESS_QUEUE.size() < crawlerConfig.getProxyAddressQueueLowThreshold()) {
                    // 重新抓取代理
                    executorService.execute(() -> crawlProxy());
                }
                // 判断线程池中是否还有正在工作的线程
                if (executorService.getActiveCount() == 0) {
                    // 结束所有线程
                    executorService.shutdown();
                    exeExpressFlag = false;
                    log.info("快递物流信息更新任务完成");
                }
            }
        }
    }

    /**
     * 从可用代理列表中获取代理
     *
     * @return
     */
    public String getFromActiveProxyList() {
        if (ACTIVE_PROXY_ADDRESS_LIST.isEmpty()) {
            while (ACTIVE_PROXY_ADDRESS_LIST.size() <= crawlerConfig.getActiveProxyListThreshold()) {
                ACTIVE_PROXY_ADDRESS_LIST.add(PROXY_ADDRESS_QUEUE.poll());
            }
        }
        Random random = new Random();
        int index = random.nextInt(ACTIVE_PROXY_ADDRESS_LIST.size());
        // log.info("获取代理：" + ACTIVE_PROXY_ADDRESS_LIST.get(index));
        return ACTIVE_PROXY_ADDRESS_LIST.get(index);
    }

    /**
     * 将代理还给可用代理列表
     *
     * @param proxy
     */
    public void returnToActiveProxyList(String proxy) {
        if (!ACTIVE_PROXY_ADDRESS_LIST.contains(proxy)) {
            ACTIVE_PROXY_ADDRESS_LIST.add(proxy);
            // log.info("重新添加代理：" + proxy);
        }
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
     * 每3小时执行一次(30分钟时)
     */
    @Scheduled(cron = "0 30 */3 * * ?")
    // @Scheduled(cron = "0 */5 * * * ?")
    public void crawlProxy() {
        if (PROXY_ADDRESS_QUEUE.size() < crawlerConfig.getProxyAddressQueueLowThreshold()) {
            // 如果代理来源页队列为空，添加来源页
            if (PROXY_SOURCES_QUEUE.isEmpty()) {
                String[] proxyList = crawlerConfig.getProxySources().split("\\|");
                for (int i = 1; i <= crawlerConfig.getProxySourcesMaxPage(); i++) {
                    for (String baseProxy : proxyList) {
                        PROXY_SOURCES_QUEUE.add(baseProxy + i);
                    }
                }
            }
            // 执行标志
            Boolean exeProxyFlag = true;
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("crawlProxy-task-" + DateUtils.getNowTime()).build();
            ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                    5,
                    5,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());
            while (exeProxyFlag) {
                // log.info("目前来源页队列有：");
                // for (String source : PROXY_SOURCES_QUEUE) {
                //     log.info(source);
                // }
                // log.info("目前代理总队列数量为：{}", PROXY_ADDRESS_QUEUE.size());

                // 判断来源页中是否有地址 并且 代理总队列数量小于最大阈值
                if (!PROXY_SOURCES_QUEUE.isEmpty() && PROXY_ADDRESS_QUEUE.size() < crawlerConfig.getProxyAddressQueueHighThreshold()) {
                    executorService.execute(() -> {
                        String url = PROXY_SOURCES_QUEUE.poll();
                        if (url == null || "".equals(url)) {
                            return;
                        }
                        log.info("解析代理页面:" + url);
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
                        } catch (Exception e) {
                            log.error("Exception" + e);
                            addToProxySourceQueue(url, "由于异常");
                        }
                        if (response != null) {
                            HttpEntity entity = response.getEntity();
                            String webContent;
                            try {
                                webContent = EntityUtils.toString(entity, "utf-8");
                                CrawlerUtils.getHostAndPort(webContent);
                                response.close();
                            } catch (Exception e) {
                                log.error("Exception" + e);
                                addToProxySourceQueue(url, "由于异常");
                            }
                        } else {
                            log.info("连接超时");
                            addToProxySourceQueue(url, "由于异常");
                        }
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            log.error("IOException" + e);
                        }
                    });
                } else {
                    //判断线程池中是否还有正在工作的线程
                    if (executorService.getActiveCount() == 0) {
                        executorService.shutdown();
                        exeProxyFlag = false;
                        log.info("代理来源页爬虫任务完成");
                    }
                }
                try {
                    // 休眠1分钟，避免被西刺封IP
                    Thread.sleep(1000 * 60 * 1);
                } catch (InterruptedException e) {
                    log.error("线程休眠报错", e);
                }
            }
        }
    }
}
