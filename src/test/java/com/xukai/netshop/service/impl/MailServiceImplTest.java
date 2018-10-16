package com.xukai.netshop.service.impl;

import com.xukai.netshop.service.MailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/16 12:13
 * @modified By:
 */
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendMail() {
        String receiver = "18350257@qq.com";
        String subject = "测试主题";
        String content = "测试内容";
        boolean result = mailService.sendSimpleMail(receiver, subject, content);
    }
}