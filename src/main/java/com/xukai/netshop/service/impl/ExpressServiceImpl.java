package com.xukai.netshop.service.impl;

import com.xukai.netshop.dataobject.ExpressInfo;
import com.xukai.netshop.enums.ExpressStatusEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.repository.ExpressInfoRepository;
import com.xukai.netshop.service.ExpressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Xukai
 * @description: 物流服务接口实现类
 * @createDate: 2018/10/25 01:44
 * @modified By:
 */
@Service
@Slf4j
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressInfoRepository expressInfoRepository;

    @Override
    public void save(ExpressInfo expressInfo) {
        ExpressInfo save = expressInfoRepository.save(expressInfo);
        if (save == null) {
            log.error("【保存物流信息】发生异常{}", ResultEnum.EXPRESS_INFO_SAVE_FAIL.getMessage());
            throw new SellException(ResultEnum.EXPRESS_INFO_SAVE_FAIL);
        }
    }

    @Override
    public void deleteByOrderId(String orderId) {
        try {
            int del = expressInfoRepository.deleteByOrderId(orderId);
            if (del == 0) {
                log.info("【删除物流信息】订单物流信息不存在, orderId={}", orderId);
            }
        } catch (Exception e) {
            throw new SellException(ResultEnum.EXPRESS_INFO_DELETE_FAIL);
        }
    }

    @Override
    public void receive(String orderId) {
        ExpressInfo expressInfo = expressInfoRepository.findOne(orderId);
        if (expressInfo == null) {
            log.error("【确认物流收货】发生异常{}", ResultEnum.EXPRESS_INFO_NOT_EXIST.getMessage());
            throw new BuyException(ResultEnum.EXPRESS_INFO_NOT_EXIST);
        }
        expressInfo.setExpressStatus(ExpressStatusEnum.HAS_RECEIVED.getCode());
        ExpressInfo save;
        try {
            save = expressInfoRepository.save(expressInfo);
            if (save == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("【确认物流收货】发生异常{}", ResultEnum.EXPRESS_INFO_SAVE_FAIL.getMessage());
            throw new BuyException(ResultEnum.EXPRESS_INFO_SAVE_FAIL);
        }
    }

    @Override
    public ExpressInfo findByOrderId(String orderId) {
        ExpressInfo expressInfo = expressInfoRepository.findOne(orderId);
        if (expressInfo == null) {
            log.error("【查找物流信息】发生异常{}", ResultEnum.EXPRESS_INFO_NOT_EXIST.getMessage());
            throw new BuyException(ResultEnum.EXPRESS_INFO_NOT_EXIST);
        }
        return expressInfo;
    }

    @Override
    public List<ExpressInfo> listInTransit() {
        List<ExpressInfo> expressInTransitList = expressInfoRepository.findByExpressStatus(0);
        return expressInTransitList;
    }
}
