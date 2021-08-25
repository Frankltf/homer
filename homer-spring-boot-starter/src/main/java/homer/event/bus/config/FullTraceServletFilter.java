package homer.event.bus.config;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Intro
 * @Author liutengfei
 */
public class FullTraceServletFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, wrapperResponse);
        wrapperResponse.resetBuffer();
        wrapperResponse.getWriter().write("ok");
        wrapperResponse.copyBodyToResponse();
        System.out.print("fe");
    }


    @Override
    public void destroy() {
    }
}
