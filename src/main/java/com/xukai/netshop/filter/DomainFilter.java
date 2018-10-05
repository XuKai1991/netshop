package com.xukai.netshop.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Xukai
 * @description: 统一过滤器设置
 * @createDate: 2018/10/2 22:01
 * @modified By:
 */
public class DomainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // 如果不需要携带cookie可以设置为“*”，否则就设置为“null”，不然这部分和设置跨域携带cookie部分会产生冲突
        response.setHeader("Access-Control-Allow-Origin", "null");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        // 设置过期时间
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        // 允许跨域请求中携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
