package com.yasas.unitandresolve.service.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserCreateRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String profilePicBase64;
}
