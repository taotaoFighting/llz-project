package com.pm.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
/**
* Created by WangShuai on 2016/7/30.
*/
public class CorsFilter implements Filter {
public void init(FilterConfig filterConfig) throws ServletException { }
public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
HttpServletResponse response = (HttpServletResponse) servletResponse;
HttpServletRequest request = (HttpServletRequest) servletRequest;
// ָ�����������������
response.setHeader("Access-Control-Allow-Origin", "*");
// ��Ӧ����
response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
// ��Ӧͷ����
response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, HaiYi-Access-Token");
if ("OPTIONS".equals(request.getMethod())){
response.setStatus(200);

}
filterChain.doFilter(servletRequest, servletResponse);
}
public void destroy() { }
}