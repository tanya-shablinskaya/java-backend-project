package com.gmail.puhovashablinskaya.security.filter;


import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.util.ConfigTokenConstants;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthCheckService authCheckService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                Date expiredDateFromJwt = jwtUtils.getExpiredDateFromJwtToken(jwt);
                if (expiredDateFromJwt.after(new Date())) {
                    if (!jwtUtils.isValidCheckUser(jwt)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                jwtUtils.getUserIdFromJwtToken(jwt), null, null
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        Long userId = jwtUtils.getUserIdFromJwtToken(jwt);
                        ResponseEntity<String> activeSession = authCheckService.checkStatusJwt(jwt);
                        if (activeSession.getStatusCode().is2xxSuccessful()) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userId, null, null
                            );
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }


    protected String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(ConfigTokenConstants.TOKEN_TYPE)) {
            return headerAuth.substring(ConfigTokenConstants.TOKEN_TYPE.length());
        }
        return null;
    }
}
