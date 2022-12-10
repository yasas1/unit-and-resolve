package com.yasas.unitandresolve.service.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {
    private final String email;
    private final String password;
}
