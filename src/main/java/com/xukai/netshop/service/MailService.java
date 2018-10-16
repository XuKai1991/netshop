package com.xukai.netshop.service;

/**
 * @author: Xukai
 * @description: 邮件service
 * @createDate: 2018/10/16 12:06
 * @modified By:
 */
public interface MailService {

    /**
     * 发送普通邮件
     *
     * @param receiver
     * @param subject
     * @param content
     * @return
     */
    boolean sendSimpleMail(String receiver, String subject, String content);

    /**
     * 发送html邮件
     *
     * @param receiver
     * @param subject
     * @param content
     * @return
     */
    boolean sendHtmlMail(String receiver, String subject, String content);

}
