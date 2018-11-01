package com.xukai.netshop.utils;

import com.xukai.netshop.config.CookieConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/10/16 11:12
 * @modified By:
 */
public class TokenUtils {

    /**
     * 从cookie中获取buyerId
     *
     * @param request
     * @return
     */
    public static String getToken(String field, HttpServletRequest request) {
        String userId;
        Cookie cookie = CookieUtils.get(field, request);
        if (cookie != null) {
            userId = cookie.getValue();
        } else {
            HttpSession session = request.getSession();
            userId = (String) session.getAttribute(field);
        }
        return userId;
    }

    /**
     * 将登录信息添加至cookie和session中
     *
     * @param fieldMap
     * @param cookieConfig
     * @param request
     * @param response
     */
    public static void addLoginTrace(Map<String, String> fieldMap, CookieConfig cookieConfig, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        for (Map.Entry<String, String> fieldEntry : fieldMap.entrySet()) {
            // 设置cookie
            CookieUtils.set(fieldEntry.getKey(), fieldEntry.getValue(), cookieConfig.getExpire(), request, response);
            // 设置session
            session.setAttribute(fieldEntry.getKey(), fieldEntry.getValue());
        }
    }

    /**
     * 清除cookie和session
     *
     * @param request
     * @param response
     */
    public static void cleanLoginTrace(String[] fields, HttpServletRequest request, HttpServletResponse response) {
        for (String field : fields) {
            // 从cookie里查询
            Cookie cookie = CookieUtils.get(field, request);
            if (cookie != null) {
                // 清除cookie
                CookieUtils.set(field, null, 0, request, response);
            }
        }
        // 清除session
        HttpSession session = request.getSession();
        for (String field : fields) {
            session.removeAttribute(field);
        }
    }
}
