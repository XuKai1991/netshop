package com.xukai.netshop.aspect;

import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.exception.SellerAuthorizeException;
import com.xukai.netshop.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/10 11:46
 * @modified By:
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private CookieConfig cookieConfig;

    /**
     * 定义切点，此处定义为controller层的任意类的任意方法
     */
    @Pointcut("execution(public * com.xukai.netshop.controller.Seller*.*(..))" +
            "&& !execution(public * com.xukai.netshop.controller.SellerUserController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doBefore() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 查询cookie
        Cookie sellerIdCookie = CookieUtils.get(cookieConfig.getSellerId(), request);
        Cookie shopIdCookie = CookieUtils.get(cookieConfig.getShopId(), request);
        if (sellerIdCookie == null || shopIdCookie == null) {
            log.error("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        CookieUtils.set(sellerIdCookie.getName(), sellerIdCookie.getValue(), cookieConfig.getExpire(), request, sra.getResponse());
        CookieUtils.set(shopIdCookie.getName(), shopIdCookie.getValue(), cookieConfig.getExpire(), request, sra.getResponse());
    }
}
