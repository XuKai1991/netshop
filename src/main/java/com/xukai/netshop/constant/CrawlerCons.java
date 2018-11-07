package com.xukai.netshop.constant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/11/6 12:17
 * @modified By:
 */
public class CrawlerCons {

    /**
     * 活动的代理地址等待队列
     */
    public final static List<String> ACTIVE_PROXY_ADDRESS_LIST = new ArrayList<>();

    /**
     * 代理地址等待队列
     */
    public final static Queue<String> PROXY_ADDRESS_QUEUE = new LinkedList<>();

    /**
     * 代理IP来源页队列
     */
    public final static Queue<String> PROXY_SOURCES_QUEUE = new LinkedList<>();

}
