// package com.xukai.netshop.Crawler;
//
// import com.xukai.netshop.config.CrawlerConfig;
// import org.apache.http.HttpEntity;
// import org.apache.http.client.config.RequestConfig;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.util.EntityUtils;
// import org.apache.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import java.io.IOException;
// import java.util.Arrays;
// import java.util.List;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ThreadPoolExecutor;
//
// import static com.xukai.netshop.constant.CrawlerCons.proxySources;
// import static com.xukai.netshop.utils.CrawlerUtils.addToProxySourceQueue;
//
// /**
//  * Author: Xukai
//  * Description: 爬取代理IP + HOST
//  * CreateDate: 2018/3/16 13:19
//  * Modified By:
//  */
// public class ProxyCrawler extends Thread {
//
//     private Logger crawlProxyLogger = Logger.getLogger(ProxyCrawler.class);
//
//     public static void main(String[] args) {
//         ProxyCrawler proxyCrawler = new ProxyCrawler();
//         proxyCrawler.start();
//     }
//
//     @Override
//     public void run() {
//         init();
//     }
//
//     @Autowired
//     private CrawlerConfig crawlerConfig;
//
//     private void init() {
//         List<String> proxyList = Arrays.asList(crawlerConfig.getProxySources().split("\\|"));
//         for (int i = 1; i < 5; i++) {
//             for (String baseProxy : proxyList) {
//                 proxySources.add(baseProxy + i);
//             }
//         }
//         // 执行标志
//         Boolean exeFlagProxy = true;
//         ExecutorService executorService = Executors.newFixedThreadPool(5);
//         while (exeFlagProxy) {
//             //判断队列中是否还有url
//             if (!proxySources.isEmpty()) {
//                 executorService.execute(() -> {
//                     String url = proxySources.poll();
//                     if (url == null || "".equals(url)) {
//                         return;
//                     }
//                     crawlProxyLogger.info("解析代理页面:" + url);
//                     // 创建HttpClient实例
//                     CloseableHttpClient httpClient = HttpClients.createDefault();
//                     // 创建httpget请求，实际开发中都用get！！！
//                     HttpGet httpGet = new HttpGet(url);
//                     RequestConfig config = RequestConfig.custom()
//                             // 设置连接超时时间10s
//                             .setConnectionRequestTimeout(10000)
//                             // 设置读取超时时间10s
//                             .setSocketTimeout(10000)
//                             .build();
//                     httpGet.setConfig(config);
//                     httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0");
//                     CloseableHttpResponse response = null;
//                     try {
//                         // 执行httpget请求
//                         response = httpClient.execute(httpGet);
//                     } catch (Exception e) {
//                         crawlProxyLogger.error("Exception" + e);
//                         addToProxySourceQueue(url, "由于异常");
//                     }
//                     if (response != null) {
//                         // 获取返回实体
//                         HttpEntity entity = response.getEntity();
//                         String webContent;
//                         try {
//                             webContent = EntityUtils.toString(entity, "utf-8");
//                             ParsePage.getHostAndPort(webContent);
//                             response.close();
//                         } catch (Exception e) {
//                             crawlProxyLogger.error("Exception" + e);
//                             addToProxySourceQueue(url, "由于异常");
//                         }
//                     } else {
//                         crawlProxyLogger.info("连接超时");
//                         addToProxySourceQueue(url, "由于异常");
//                     }
//                     try {
//                         httpClient.close();
//                     } catch (IOException e) {
//                         crawlProxyLogger.error("IOException" + e);
//                     }
//
//                 });
//             } else {
//                 //判断线程池中是否还有正在工作的线程
//                 if (((ThreadPoolExecutor) executorService).getActiveCount() == 0) {
//                     executorService.shutdown();
//                     exeFlagProxy = false;
//                     crawlProxyLogger.info("代理来源页爬虫任务完成");
//                 }
//             }
//             try {
//                 Thread.sleep(1000);
//             } catch (InterruptedException e) {
//                 crawlProxyLogger.error("线程休眠报错", e);
//             }
//         }
//     }
//
// }
