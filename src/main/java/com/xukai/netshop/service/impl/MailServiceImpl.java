package com.xukai.netshop.service.impl;

import com.xukai.netshop.config.MailConfig;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author: Xukai
 * @description: 邮件service实现类
 * @createDate: 2018/10/16 12:08
 * @modified By:
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    // @Value("${mail.fromMail.sender}")
    // private String sender;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendSimpleMail(String receiver, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.getSender());
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("【发送普通邮件】发生异常{}", e);
        }
        return true;
    }

    @Override
    public boolean sendHtmlMail(String receiver, String subject, String content) {
        String html = "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>买家登录</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table style=\"-webkit-text-size-adjust: 100%; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 310px; margin: 0 auto;\"\n" +
                "       width=\"310\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n" +
                "    <tbody>\n" +
                "    <tr style=\"page-break-before: always\">\n" +
                "        <td valign=\"top\">\n" +
                "            <h1 style=\"font-family: sans-serif; font-size: 21px; line-height: 29px; font-weight: normal; margin: 0 0 11px 0; text-align: center;\">\n" +
                "                找回密码</h1>\n" +
                "            <p class=\"primary\"\n" +
                "               style=\"font-family: sans-serif; font-size: 14px; line-height: 21px; font-weight: normal; margin: 0 0 21px 0; text-align: center;\">\n" +
                "                您的密码是：" + content + "</p>\n" +
                "            <p>\n" +
                "            </p>\n" +
                "            <p style=\"font-family:sans-serif; font-size: 13px; line-height: 20px; font-weight: normal; margin: 0 0 24px 0px; text-align: center; color: #4a4a4f;\">\n" +
                "                请牢记您的密码\n" +
                "            </p>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr height=\"50\">\n" +
                "        <td valign=\"top\" align=\"center\">\n" +
                "            <table id=\"email-button\"\n" +
                "                   style=\"-webkit-text-size-adjust: 100%; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #0a84ff; border-radius: 4px; height: 50px; width: 310px !important;\"\n" +
                "                   width=\"100%\" height=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n" +
                "                <tbody>\n" +
                "                <tr style=\"page-break-before: always\">\n" +
                "                    <td id=\"button-content\"\n" +
                "                        style=\"font-family: sans-serif; font-weight: normal; text-align: center; margin: 0; color: #ffffff; font-size: 20px; line-height: 100%;\"\n" +
                "                        valign=\"middle\" align=\"center\">\n" +
                "\n" +
                "                        <a href=\"#\"\n" +
                "                           id=\"button-link\"\n" +
                "                           style=\"font-family:sans-serif; color: #fff; display: block; padding: 15px; text-decoration: none; width: 280px; font-size: 18px; line-height: 26px;\"\n" +
                "                           rel=\"noopener\" target=\"_blank\">重新登录</a>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "\n" +
                "    <tr style=\"page-break-before: always\">\n" +
                "        <td border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" height=\"100%\">\n" +
                "            </br>\n" +
                "            <p class=\"secondary\"\n" +
                "               style=\"font-family: sans-serif; font-weight: normal; margin: 0 0 12px 0; text-align: center; color: #737373; font-size: 11px; line-height: 18px; width: 310px !important; word-wrap:break-word\">\n" +
                "                如果您怀疑有人在试图访问您的账户，请更改您的密码。\n" +
                "            </p>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailConfig.getSender());
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("【发送html邮件】发生异常{}", e);
            throw new BuyException(ResultEnum.GET_BACK_PSD_FAIL);
        }
        return true;
    }
}
