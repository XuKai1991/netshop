package com.xukai.netshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 14:04
 * @modified By:
 */
@RestController
@RequestMapping("/buyer/cart")
@Slf4j
public class BuyerCartController {

    @GetMapping("/add")
    public String addCart(@RequestParam("productId")String productId, @RequestParam("productQuantity")String productQuantity){



        return "";
    }
}
