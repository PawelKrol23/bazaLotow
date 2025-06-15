package org.example.bazalotow2.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
public class ServerResponseTimeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ServerResponseTimeFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (request instanceof HttpServletRequest httpRequest) {
                String method = httpRequest.getMethod();
                String uri = httpRequest.getRequestURI();
                logger.info("[{} {}] Czas odpowiedzi: {} ms", method, uri, duration);
            }
        }
    }
}
