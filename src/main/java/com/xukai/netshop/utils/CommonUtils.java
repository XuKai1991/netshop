package com.xukai.netshop.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/16 11:12
 * @modified By:
 */
public class CommonUtils {

    public static String getBuyerId(HttpServletRequest request) {
        String buyerId;
        Cookie cookie = CookieUtils.get("buyerId", request);
        if (cookie != null) {
            buyerId = cookie.getValue();
        } else {
            HttpSession session = request.getSession();
            buyerId = (String) session.getAttribute("buyerId");
        }
        return buyerId;
    }
}
