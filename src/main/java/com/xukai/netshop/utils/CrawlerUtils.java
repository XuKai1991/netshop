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
                    CrawlerUtils.addToProxyQueue(ip + ":" + port, "解析西刺代理页面");
                }
            }
        }
    }

    /**
     * 将抓取的ip和port添加至proxy队列，假如队列中存在则不添加
     *
     * @param proxy
     * @param info
     */
    public static void addToProxyQueue(String proxy, String info) {
        if (StringUtils.isEmpty(proxy)) {
            return;
        }
        if (info == null) {
            info = "";
        }
        if (!PROXY_ADDRESS_QUEUE.contains(proxy)) {
            PROXY_ADDRESS_QUEUE.add(proxy);
        }
        log.info("[" + info + "]" + "代理总队列添加代理");
    }

    /**
     * 将url添加至代理来源页等待队列，假如队列中存在则不添加
     *
     * @param proxy
     * @param info
     */
    public static void addToProxySourceQueue(String proxy, String info) {
        if (StringUtils.isEmpty(proxy)) {
            return;
        }
        if (info == null) {
            info = "";
        }
        if (!PROXY_SOURCES_QUEUE.contains(proxy)) {
            PROXY_SOURCES_QUEUE.add(proxy);
            log.info("[" + info + "]" + "代理来源页队列添加url：" + proxy);
        }
    }

}
