package com.workspace.tokenAuthenticationBackend.domain.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginData {
    String id;
    String email;
    String password;
}
