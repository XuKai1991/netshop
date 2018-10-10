package com.xukai.netshop.config;

import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.PictureService;
import com.xukai.netshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Xukai
 * @description: 定时任务
 * @createDate: 2018/10/10 11:09
 * @modified By:
 */
@Configuration
@Slf4j
public class ScheduledTasks {

    @Autowired
    private ProductService productService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private BaseUrlConfig baseUrlConfig;

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

    /**
     * 每三天执行一次
     */
    @Scheduled(cron = "0 0 0 */3 * ?")
    public void clearPicFromFastdfs() {
        log.info("【定时任务 - 清理过期图片】每三天执行一次");
        String savedPicUrlsStr = "";
        BufferedReader reader = null;
        File file = new File("PicSaveLog.txt");
        try {
            int fileLength = (int) file.length();
            reader = new BufferedReader(new FileReader("PicSaveLog.txt"));
            char[] c = new char[fileLength];
            reader.read(c);
            savedPicUrlsStr = new String(c);
        } catch (Exception e) {
            log.error("【卖家端清理过期图片】发生异常{}", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> savedPicUrls = new ArrayList<>();
        for (String savePicUrl : savedPicUrlsStr.split("\r\n")) {
            savedPicUrls.add(savePicUrl);
        }
        ArrayList<String> existPicUrls = new ArrayList<>();
        Page<ProductInfo> productInfos = productService.findAll(null);
        for (ProductInfo productInfo : productInfos) {
            existPicUrls.add(productInfo.getProductImgMd().replace(baseUrlConfig.image_server_url, ""));
            List<String> detailImgUrls = Arrays.asList(productInfo.getProductDetailImg().split("\\|"));
            for (String detailImgUrl : detailImgUrls) {
                existPicUrls.add(detailImgUrl.replace(baseUrlConfig.image_server_url, ""));
            }
        }
        savedPicUrls.removeAll(existPicUrls);
        // log.info(existPicUrls.toString());
        // log.info("======================");
        // log.info(savedPicUrls.toString());
        for (String toDelPicUrl : savedPicUrls) {
            try {
                pictureService.deleteFile(toDelPicUrl);
            } catch (SellException e) {
                log.error("【卖家端清理过期图片】发生异常{}", e);
            }
        }
        // 将现存的PicUrl存入文本
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("PicSaveLog.txt", false));
            writer.write("");
            writer = new BufferedWriter(new FileWriter("PicSaveLog.txt", true));
            for (String existPicUrl : existPicUrls) {
                writer.write(existPicUrl + "\r\n");
            }
        } catch (IOException e) {
            log.error("【卖家端清理过期图片】发生异常{}", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("【卖家端清理过期图片】发生异常{}", e);
            }
        }
    }
}
