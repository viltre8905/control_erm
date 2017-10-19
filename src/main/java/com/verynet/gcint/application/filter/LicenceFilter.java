package com.verynet.gcint.application.filter;

import com.verynet.gcint.api.util.GeneralUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by day on 09/10/2016.
 */
public class LicenceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url = httpServletRequest.getRequestURL().toString();
        if (url.contains("/login")) {
            File file = new File(GeneralUtil.getApplicationDataDirectory() + "/key");
            if (!file.exists()) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/licence");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
