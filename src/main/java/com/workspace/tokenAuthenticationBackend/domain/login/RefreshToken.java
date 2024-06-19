package com.workspace.tokenAuthenticationBackend.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RefreshToken {
    String tokenValue;
    String email;
}
