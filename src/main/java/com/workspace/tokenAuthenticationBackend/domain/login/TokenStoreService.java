package com.workspace.tokenAuthenticationBackend.domain.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TokenStoreService {
    private final Set<RefreshToken> tokenStore = ConcurrentHashMap.newKeySet();

    public void setToken(String refreshToken, String email) {
        tokenStore.add(new RefreshToken(refreshToken, email));
        tokenStore.forEach(token -> {
            log.info("{} {}", token.tokenValue, token.email);
        });
    }

    public boolean removeToken(String refreshToken) {
        return tokenStore.removeIf(token -> Objects.equals(token.tokenValue, refreshToken));
    }

    public String getEmail(String refreshToken) {
        tokenStore.forEach(token -> {
            log.info("{} {}", token.tokenValue, token.email);

        });

        return tokenStore.stream().filter(token -> Objects.equals(token.tokenValue, refreshToken))
                .findAny()
                .map(RefreshToken::getEmail)
                .orElse(null);
    }
}
