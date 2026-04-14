package com.anastasiia.itkacademy.configuration;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        logger.info(String.format("%s %s %d time=%d ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration));
    }
}