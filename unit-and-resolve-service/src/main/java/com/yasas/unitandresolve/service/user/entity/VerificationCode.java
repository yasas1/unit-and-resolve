package com.yasas.unitandresolve.service.user.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table(name = "public.verification")
public class VerificationCode {
    @Id
    private String id;
    @Column(value = "code")
    private String code;
    @Column(value = "expirationtime")
    private long expirationTime;
    @Column(value = "userid")
    private String userId;

    @Transient
    private User user;
}
