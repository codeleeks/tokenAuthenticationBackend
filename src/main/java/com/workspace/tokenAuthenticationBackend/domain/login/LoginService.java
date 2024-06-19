package com.workspace.tokenAuthenticationBackend.domain.login;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {
    private final ConcurrentHashMap<String, LoginData> loginDataStore = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        loginDataStore.put("codeleeks@naver.com", new LoginData(UUID.randomUUID().toString(),
                "codeleeks@naver.com", "password"));
    }

    public boolean login(String email, String password) {
        LoginData loginData = loginDataStore.get(email);

        if (loginData == null) {
            return false;
        }

        return Objects.equals(email, loginData.email) && Objects.equals(password, loginData.password);
    }
}
