package com.workspace.tokenAuthenticationBackend.web.login;

import com.workspace.tokenAuthenticationBackend.domain.login.LoginService;
import com.workspace.tokenAuthenticationBackend.domain.login.TokenStoreService;
import com.workspace.tokenAuthenticationBackend.web.jwt.JWTGenerator;
import com.workspace.tokenAuthenticationBackend.web.jwt.JWTTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    private final TokenStoreService tokenStoreService;
    @GetMapping("/login")
    public void loginView() {
      log.info("login view");
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkLogin(@RequestBody LoginForm loginForm,
                                     HttpServletResponse response) {
        boolean canLogin = loginService.login(loginForm.email, loginForm.password);
        if (!canLogin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        //로그인 성공 처리
        //토큰 발급

        JWTGenerator jwtGenerator = new JWTGenerator();
        String accessToken = jwtGenerator.accessToken(loginForm.email);
        String refreshToken = jwtGenerator.refreshToken(loginForm.email);

//        리프레시 토큰을 디비에 저장
        tokenStoreService.setToken(refreshToken, loginForm.email);

        return ResponseEntity.ok()
                .header("x-access-token", accessToken)
                .header("x-refresh-token", refreshToken)
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("x-refresh-token");
        String email = tokenStoreService.getEmail(refreshTokenHeader);
        if (email != null) {
            tokenStoreService.removeToken(refreshTokenHeader);

            boolean hasTokenExpired = new JWTTokenProvider().hasTokenExpired(refreshTokenHeader);
            if (!hasTokenExpired) {
                JWTGenerator jwtGenerator = new JWTGenerator();
                String accessToken = jwtGenerator.accessToken(email);
                String refreshToken = jwtGenerator.refreshToken(email);

                tokenStoreService.setToken(refreshToken, email);

                return ResponseEntity.ok()
                        .header("x-access-token", accessToken)
                        .header("x-refresh-token", refreshToken)
                        .build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("x-refresh-token");
        boolean removed = tokenStoreService.removeToken(refreshTokenHeader);
        log.info("{} removed : {}", refreshTokenHeader, removed);
        if (removed) {
            return ResponseEntity.ok()
                    .build();
        } else {
            return ResponseEntity.notFound()
                    .build();
        }
    }
}
