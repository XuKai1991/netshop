package com.xukai.netshop.scheduledTasks;

import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.dataobject.ProductInfo;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.PictureService;
import com.xukai.netshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Xukai
 * @description: FastDfs图片任务
 * @createDate: 2018/10/10 11:09
 * @modified By:
 */
@Configuration
@Slf4j
public class FastdfsPicTask {

    @Autowired
    private ProductService productService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private BaseUrlConfig baseUrlConfig;

    /**
     * 每六小时执行一次旧图片清理
     */
    @Scheduled(cron = "0 0 */6 * * ?")
    public void clearPicFromFastdfs() {
        log.info("【定时任务 - 清理过期图片】每六小时执行一次");
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
