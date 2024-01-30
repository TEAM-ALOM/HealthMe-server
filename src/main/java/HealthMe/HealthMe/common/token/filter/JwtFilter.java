package HealthMe.HealthMe.common.token.filter;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.common.token.AuthTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);


        try {
            if(token != null && tokenProvider.validateToken(token)){
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException e) {
            handleException(response, e);
            return;
        }


        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, CustomException e) throws IOException {
        log.info("Error code = {}", e.getErrorCode().getHttpStatus().value());
        response.setStatus(e.getErrorCode().getHttpStatus().value());
        response.getWriter().write(e.getMessage());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){

        String[] excludePath = {"/api/user/login", "/api/user/signup",
                "/api/refresh", "api/email/**", "/v3/api-docs/**",
                "/swagger-ui/**",
                "/api/user/change-forget-password",
                "/actuator/**",
                "/metrics"};
        // jwt 인증 미 실시 토큰

        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
