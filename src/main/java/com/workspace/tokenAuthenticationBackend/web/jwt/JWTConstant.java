package com.workspace.tokenAuthenticationBackend.web.jwt;

import java.time.Duration;

public class JWTConstant {
    public static final long accessDuration = Duration.ofSeconds(3).toMillis();
    public static final long refreshDuration = Duration.ofSeconds(10).toMillis();
}
