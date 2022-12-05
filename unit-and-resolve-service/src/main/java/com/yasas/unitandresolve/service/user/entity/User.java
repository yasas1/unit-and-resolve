package com.yasas.unitandresolve.service.user.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table(name = "public.user")
public class User {

    @Id
    private long id;
    @Column(value = "email")
    private String email;
    @Column(value = "firstname")
    private String firstName;
    @Column(value = "lastname")
    private String lastName;
    @Column(value = "password")
    private String password;
    @Column(value = "profilepicbase64")
    private String profilePicBase64;
    @Column(value = "createddatetime")
    private Long createdDateTime;
    @Column(value = "lastmodifieddatetime")
    private Long lastModifiedDateTime;
}
