package com.xukai.netshop.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.service.PictureService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/4 13:28
 * @modified By:
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String uploadFile(MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new SellException(ResultEnum.PICTURE_EMPTY);
        }
        //上传到服务器并返回路径
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SellException(ResultEnum.PICTURE_UPLOAD_FAIL);
        }
    }

    @Override
    public String uploadImageAndCrtThumbImage(MultipartFile file) {
        // 判断图片是否为空
        if (file.isEmpty()) {
            throw new SellException(ResultEnum.PICTURE_EMPTY);
        }
        //上传图片到服务器并返回路径
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SellException(ResultEnum.PICTURE_UPLOAD_FAIL);
        }
    }

    @Override
    public String uploadFile(String content, String fileExtension) {
        if (StringUtils.isEmpty(content)) {
            throw new SellException(ResultEnum.CONTENT_EMPTY);
        }
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
        return storePath.getFullPath();
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            throw new SellException(ResultEnum.CONTENT_EMPTY);
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            throw new SellException(ResultEnum.PICTURE_DELETE_FAIL);
        }
    }

}
