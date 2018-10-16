package com.xukai.netshop.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xukai.netshop.dataobject.BuyerInfo;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.repository.BuyerInfoRepository;
import com.xukai.netshop.service.BuyerService;
import com.xukai.netshop.service.MailService;
import com.xukai.netshop.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/25 11:43
 * Modified By:
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerInfoRepository buyerInfoRepository;

    @Autowired
    private MailService mailService;

    @Override
    public boolean checkUsername(String username) {
        BuyerInfo checkUsername = buyerInfoRepository.findByUsername(username);
        return checkUsername == null ? false : true;
    }

    @Override
    public boolean checkEmail(String email) {
        BuyerInfo checkEmail = buyerInfoRepository.findByEmail(email);
        return checkEmail == null ? false : true;
    }

    @Override
    public BuyerInfo save(BuyerInfo buyerInfo) {
        buyerInfo.setBuyerId(KeyUtils.genUUID());
        return buyerInfoRepository.save(buyerInfo);
    }

    @Override
    public BuyerInfo findByUsernameAndPassword(String username, String password) {
        return buyerInfoRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public void getBackPassword(String email) {
        BuyerInfo buyerInfo = buyerInfoRepository.findByEmail(email);
        if (buyerInfo == null) {
            throw new BuyException(ResultEnum.EMAIL_NOT_EXIST);
        }
        String receiver = email;
        String subject = "找回密码";
        String content = buyerInfo.getPassword();
        try {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("send-email-" + buyerInfo.getBuyerId()).build();
            ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor(
                    1,
                    1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(),
                    namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());
            threadExecutor.submit(() -> {
                mailService.sendHtmlMail(receiver, subject, content);
            });
        } catch (Exception e) {
            throw new BuyException(ResultEnum.GET_BACK_PSD_FAIL);
        }
    }
}
