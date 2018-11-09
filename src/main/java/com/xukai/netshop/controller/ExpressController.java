package com.xukai.netshop.controller;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.dataobject.ExpressInfo;
import com.xukai.netshop.enums.ExpressShipperEnum;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.scheduledTasks.ExpressTask;
import com.xukai.netshop.service.ExpressService;
import com.xukai.netshop.utils.EnumUtils;
import com.xukai.netshop.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.xukai.netshop.constant.CrawlerCons.EXPRESS_REFRESH_TRIGGER_EXECUTOR;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/11/6 21:42
 * @modified By:
 */
@RestController
@RequestMapping("/express")
@Slf4j
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    @Autowired
    private ExpressTask expressTask;

    /**
     * 根据订单号查询物流
     *
     * @param orderId
     * @return
     */
    @GetMapping("/findOne")
    public ResultVO findOne(String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查找物流信息】发生异常{}", ResultEnum.PARAM_ERROR.getMessage());
            throw new BuyException(ResultEnum.PARAM_ERROR);
        }
        ExpressInfo expressInfo = expressService.findByOrderId(orderId);
        return ResultVOUtil.success(expressInfo);
    }

    /**
     * 手动刷新物流信息
     *
     * @return
     */
    @GetMapping("/refreshLogistics")
    public ResultVO refreshLogistics() {
        EXPRESS_REFRESH_TRIGGER_EXECUTOR.execute(() -> expressTask.refreshLogisticsDetail());
        return ResultVOUtil.success();
    }

    /**
     * 获取订单状态枚举列表
     *
     * @return
     */
    @GetMapping("/listShippers")
    public ResultVO listStatus() {
        Map<String, String> ExpressShipperEnumMap = EnumUtils.listEnum(ExpressShipperEnum.class);
        return ResultVOUtil.success(ExpressShipperEnumMap);
    }
}
