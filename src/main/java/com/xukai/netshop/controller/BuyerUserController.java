package com.xukai.netshop.controller;

import com.xukai.netshop.dataobject.BuyerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 14:28
 * @modified By:
 */
@Controller
@Slf4j
public class BuyerUserController {

    @PostMapping("/register")
    public ModelAndView register(BuyerInfo buyerInfo) {
        ModelAndView mav = new ModelAndView();

        return mav;
    }
}
