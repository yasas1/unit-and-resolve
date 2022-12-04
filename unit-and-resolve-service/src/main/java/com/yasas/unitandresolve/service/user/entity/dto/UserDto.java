package com.yasas.unitandresolve.service.user.entity.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicBase64;
    private Long createdDateTime;
    private Long lastModifiedDateTime;
}
