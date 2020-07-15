package cn.tanglaoer.springboot.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class HelloFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("HelloFilter............放行之前");

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        StringBuffer requestURL = req.getRequestURL();
        System.out.println(requestURL.toString());

        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("HelloFilter............放行之后");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
