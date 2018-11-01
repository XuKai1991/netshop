package com.xukai.netshop.scheduledTasks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/31 21:51
 * @modified By:
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 通过配置TaskScheduler，可以使用多线程执行定时任务
     * 否则在单线程条件下，如果定时任务时间重合就会发生阻塞
     * 导致只能有一个任务被成功执行
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 线程池大小
        taskScheduler.setPoolSize(10);
        // 线程池名称前缀
        taskScheduler.setThreadNamePrefix("netshop-task");
        return taskScheduler;
    }
}
