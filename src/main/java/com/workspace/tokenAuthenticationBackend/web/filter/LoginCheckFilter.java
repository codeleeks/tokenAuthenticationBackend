package com.workspace.tokenAuthenticationBackend.web.filter;

import com.workspace.tokenAuthenticationBackend.web.jwt.JWTTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (Objects.equals(httpRequest.getMethod(), "OPTIONS")) {
            log.info("OPTION packet accepted");
            chain.doFilter(request, response);
            return;
        }
        String accessTokenHeader = httpRequest.getHeader("x-access-token");

        boolean hasTokenExpired = jwtTokenProvider.hasTokenExpired(accessTokenHeader);
        if (hasTokenExpired) {
            ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(httpResponse);
            chain.doFilter(request, wrapperResponse);
            wrapperResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }


//            String refreshTokenHeader = httpRequest.getHeader("x-refresh-token");
//            try {
//                Claims claims = jwtTokenProvider.parseJwtToken(refreshTokenHeader);
//                String email = (String) claims.get("email");
//                ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(httpResponse);
//                chain.doFilter(request, wrapperResponse);
//                wrapperResponse.setHeader("x-access-token", new JWTGenerator().accessToken(email));
//                wrapperResponse.setHeader("x-refresh-token", new JWTGenerator().refreshToken(email));
//                wrapperResponse.copyBodyToResponse();
//                return;
//            } catch (ExpiredJwtException innerE) {
//                log.info("로그인 다시 하세요~");
//                ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(httpResponse);
//                chain.doFilter(request, wrapperResponse);
//                wrapperResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }


        chain.doFilter(request, response);

//        https://medium.com/sjk5766/spring-filter%EC%97%90%EC%84%9C-response-%EC%88%98%EC%A0%95%ED%95%98%EA%B8%B0-7de6da9836f5
    }
}
