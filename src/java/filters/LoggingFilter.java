package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

//@WebFilter("/*")  // Áp dụng cho tất cả request
public class LoggingFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        // Lấy thông tin request
        String ipAddress = request.getRemoteAddr();
        String requestURL = req.getRequestURI();
        String method = req.getMethod();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Ghi log ra console
        System.out.println("LOG: [" + timestamp + "] " + method + " request from " + ipAddress + " to " + requestURL);

        // Cho request tiếp tục xử lý
        chain.doFilter(request, response);
    }

    public void destroy() {}
}
