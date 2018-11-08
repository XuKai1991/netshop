package com.xukai.netshop.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

import static com.xukai.netshop.constant.CrawlerCons.PROXY_ADDRESS_QUEUE;
import static com.xukai.netshop.constant.CrawlerCons.PROXY_SOURCES_QUEUE;

/**
 * @author: Xukai
 * @description: 爬虫相关Utils
 * @createDate: 2018/11/6 12:15
 * @modified By:
 */
@Slf4j
public class CrawlerUtils {

    /**
     * 解析代理来源页页面内容得到IP + HOST
     *
     * @param webContent
     */
    public static void getHostAndPort(String webContent) {
        if (webContent != null && !"".equals(webContent)) {
            String ip = null;
            String port = null;
            // 解析网页获取文档对象
            Document doc = Jsoup.parse(webContent);
            Elements selectElements = doc.select("td");
            for (Element e : selectElements) {
                if (Pattern.matches("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)", e.text())) {
                    ip = e.text();
                }
                if (Pattern.matches("[1-9]\\d*", e.text())) {
                    port = e.text();
                    CrawlerUtils.addToProxyQueue(ip + ":" + port);
                }
            }
        }
    }

    /**
     * 将抓取的ip和port添加至proxy队列，假如队列中存在则不添加
     *
     * @param proxy
     */
    public static void addToProxyQueue(String proxy) {
        if (StringUtils.isEmpty(proxy)) {
            return;
        }
        if (!PROXY_ADDRESS_QUEUE.contains(proxy)) {
            PROXY_ADDRESS_QUEUE.add(proxy);
            log.info("代理总队列添加url：" + proxy);
        }
    }

    /**
     * 将url添加至代理来源页等待队列，假如队列中存在则不添加
     *
     * @param source
     * @param info
     */
    public static void addToProxySourceQueue(String source, String info) {
        if (StringUtils.isEmpty(source)) {
            return;
        }
        if (info == null) {
            info = "";
        }
        if (!PROXY_SOURCES_QUEUE.contains(source)) {
            PROXY_SOURCES_QUEUE.add(source);
            log.info("【抓取代理】" + info + "代理来源页队列添加url：" + source);
        }
    }

}
