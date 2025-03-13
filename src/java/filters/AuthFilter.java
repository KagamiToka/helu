package filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")  // Áp dụng cho tất cả request
public class AuthFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String role = (session != null) ? (String) session.getAttribute("role") : null;
//        String role = session;
//        if(session != null){
//            session.getAttribute("role");
//        }else {
//            return null;
//        }
        String path = req.getRequestURI();
        String action = req.getParameter("action");

        System.out.println("DEBUG: AuthFilter - Path = " + path + ", Action = " + action + ", Role = " + role);

        // CHẶN USER TRUY CẬP `CREATE`, `EDIT`, `DELETE`
        if ((action != null) && (action.equals("create") || action.equals("edit") || action.equals("delete"))) {
            if (role == null || !"admin".equals(role)) {
                System.out.println("DEBUG: User không có quyền, chặn request " + path);
                res.sendRedirect(req.getContextPath() + "/products?error=notAdmin");
                return;
            }
        }

        // CHO PHÉP TRUY CẬP CÁC TRANG KHÁC
        chain.doFilter(request, response);
    }

    public void destroy() {}
}
