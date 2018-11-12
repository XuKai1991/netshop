package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.config.FastDfsConfig;
import com.xukai.netshop.service.PictureService;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/4 13:42
 * @modified By:
 */
@RestController
@RequestMapping("/seller/pic")
@Slf4j
public class SellerPictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private BaseUrlConfig baseUrlConfig;

    @Autowired
    private FastDfsConfig fastDfsConfig;

    @PostMapping("/upload")
    public ResultVO uploadPic(MultipartFile file, HttpServletRequest request) {
        String picUrl = pictureService.uploadFile(file);
        // 记录所有保存过的图片Url
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fastDfsConfig.getPsicSaveLogUrl(), true));
            writer.write(picUrl + "\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success(baseUrlConfig.imageServerUrl + picUrl);
    }

}
