package com.yqsh.diningsys.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器，杜绝登录浏览器直接输入登录请求出现错误
 *
 * @author zhshuo create on 2016-12-02 下午1:08
 */
public class LoginFilter implements Filter{

    public FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        String method = httpServletRequest.getMethod();
        if(method.equalsIgnoreCase("get")){
            httpServletResponse.sendRedirect("DININGSYS/page/login");
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}
