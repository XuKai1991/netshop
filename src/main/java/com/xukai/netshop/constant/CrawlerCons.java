package com.xukai.netshop.constant;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xukai.netshop.utils.DateUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /**
     * 随机数
     */
    public final static Random RANDOM = new Random();

    /**
     * 物流信息更新线程池
     */
    public static ThreadPoolExecutor EXPRESS_CRAWLER_THREAD_EXECUTOR = new ThreadPoolExecutor(
            10,
            10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("refreshLogisticsDetailTask-" + DateUtils.getNowTime()).build(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 手动触发更新物流任务线程池
     * corePoolSize = 1
     * maximumPoolSize = 1
     * workQueue(任务队列)：LinkedBlockingQueue<>(1)，容量为1的链表队列
     * RejectedExecutionHandler （饱和策略）：DiscardPolicy策略，即不处理，丢弃掉
     */
    public static ThreadPoolExecutor EXPRESS_REFRESH_TRIGGER_EXECUTOR = new ThreadPoolExecutor(
            1,
            1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1),
            new ThreadFactoryBuilder().setNameFormat("refreshLogisticsDetailTaskTrigger--" + DateUtils.getNowTime()).build(),
            new ThreadPoolExecutor.DiscardPolicy());

}
