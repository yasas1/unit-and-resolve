package com.yasas.unitandresolve.service.user.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Data
@Table(name = "public.verification")
public class VerificationCode {
    @Id
    private int id;
    @Column(value = "code")
    private String code;
    @Column(value = "expirationtime")
    private long expirationTime;
    @Column(value = "useremail")
    private String userEmail;
    @Column(value = "createddatetime")
    private Long createdDateTime;
}
