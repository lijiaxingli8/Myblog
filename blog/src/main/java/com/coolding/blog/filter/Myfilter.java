package com.coolding.blog.filter;



/**
 * @ClassName Myfilter
 * @Author 酷酷的丁
 * @Date 2020-03-30 14:28
 */
import javax.servlet.*;
import java.io.IOException;

public class Myfilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter process...!!!!!!!!!!!!!");

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}