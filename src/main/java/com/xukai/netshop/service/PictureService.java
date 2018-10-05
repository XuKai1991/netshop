package com.xukai.netshop.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Xukai
 * @description: 图片service
 * @createDate: 2018/10/4 13:27
 * @modified By:
 */
public interface PictureService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     *
     * @param file
     * @return
     */
    String uploadImageAndCrtThumbImage(MultipartFile file);

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    String uploadFile(String content, String fileExtension);

    /**
     * 删除文件
     *
     * @param fileUrl
     */
    void deleteFile(String fileUrl);

}
