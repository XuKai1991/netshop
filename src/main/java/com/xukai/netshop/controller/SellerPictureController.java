package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.service.PictureService;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upload")
    public ResultVO uploadPic(MultipartFile file) {
        String fileUrl = pictureService.uploadFile(file);
        return ResultVOUtil.success(baseUrlConfig.image_server_url + fileUrl);
    }

    @GetMapping("/delete")
    public ResultVO deletePic(String picUrl) {
        picUrl = picUrl.replace(baseUrlConfig.image_server_url, "");
        pictureService.deleteFile(picUrl);
        return ResultVOUtil.success();
    }

}
