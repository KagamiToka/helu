package filters;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

//@WebFilter("/*")  // Áp dụng cho tất cả request
public class CompressionFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResp = (HttpServletResponse) response;

        // Kiểm tra nếu trình duyệt hỗ trợ Gzip
        String acceptEncoding = ((HttpServletRequest) request).getHeader("Accept-Encoding");
        if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
            // Đặt header để trình duyệt biết dữ liệu được nén
            httpResp.setHeader("Content-Encoding", "gzip");

            // Gửi phản hồi đã nén đến trình duyệt
            GzipResponseWrapper gzipResponse = new GzipResponseWrapper(httpResp);
            chain.doFilter(request, gzipResponse);
            gzipResponse.close();
            return;
        }

        // Nếu không hỗ trợ Gzip, tiếp tục request bình thường
        chain.doFilter(request, response);
    }

    public void destroy() {}

    // Lớp wrapper để nén dữ liệu phản hồi bằng Gzip
    private static class GzipResponseWrapper extends HttpServletResponseWrapper {
        private GZIPOutputStream gzipOutputStream;
        private ServletOutputStream servletOutputStream;

        public GzipResponseWrapper(HttpServletResponse response) throws IOException {
            super(response);
            this.servletOutputStream = response.getOutputStream();
            this.gzipOutputStream = new GZIPOutputStream(servletOutputStream);
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    gzipOutputStream.write(b);
                }

                @Override
                public boolean isReady() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            };
        }

        @Override
        public void flushBuffer() throws IOException {
            gzipOutputStream.flush();
        }

        public void close() throws IOException {
            gzipOutputStream.close();
        }
    }
}
