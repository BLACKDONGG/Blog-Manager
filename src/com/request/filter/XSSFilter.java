package com.request.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.request.filter.XssFilterWrapper;
public class XSSFilter implements Filter {  
	
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
//    	request.setCharacterEncoding(arg0)
        chain.doFilter(new XssFilterWrapper((HttpServletRequest) request), response);
    }
}