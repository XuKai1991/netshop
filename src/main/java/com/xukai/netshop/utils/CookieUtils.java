package com.xukai.netshop.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Xukai
 * @description: Cookie工具
 * @createDate: 2018/7/10 10:17
 * @modified By:
 */
public class CookieUtils {

    /**
     * 设置cookie
     *
     * @param name
     * @param value
     * @param maxAge
     * @param response
     */
    public static void set(String name, String value, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        //设置cookie的有效时间，以秒为单位
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        // cookie.setDomain("");
        //将cookie添加至response
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(String name, HttpServletRequest request) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装成Map
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
