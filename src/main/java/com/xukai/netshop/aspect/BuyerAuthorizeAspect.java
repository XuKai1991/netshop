package com.xukai.netshop.aspect;

import com.xukai.netshop.config.CookieConfig;
import com.xukai.netshop.exception.BuyerAuthorizeException;
import com.xukai.netshop.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/10 11:46
 * @modified By:
 */
@Aspect
@Component
@Slf4j
public class BuyerAuthorizeAspect {

    @Autowired
    private CookieConfig cookieConfig;

    /**
     * 定义切点，此处定义为controller层的任意类的任意方法
     */
    @Pointcut("execution(public * com.xukai.netshop.controller.BuyerCart*.*(..))" +
            "|| execution(public * com.xukai.netshop.controller.BuyerOrder*.*(..))" +
            "|| execution(public * com.xukai.netshop.controller.BuyerAddress*.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doBefore() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 查询cookie
        Cookie cookie = CookieUtils.get(cookieConfig.getBuyerId(), request);
        if (cookie == null) {
            // 查询session
            HttpSession session = request.getSession();
            String buyerId = (String) session.getAttribute(cookieConfig.getBuyerId());
            if (StringUtils.isNotEmpty(buyerId)) {
                return;
            }
            log.error("【登录校验】Cookie或Session中查不到buyerId");
            throw new BuyerAuthorizeException();
        }
        HttpServletResponse response = sra.getResponse();
        CookieUtils.set(cookie.getName(), cookie.getValue(), cookieConfig.getExpire(), request, response);
    }
}
