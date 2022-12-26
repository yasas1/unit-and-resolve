package com.yasas.unitandresolve.service.unit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "public.user_unit")
public class UserUnit {
    @Id
    private long id;
    @Column(value = "userid")
    private long userId;
    @Column(value = "unitid")
    private long unitId;
    @Column(value = "isadmin")
    private boolean isAdmin;
    @Column(value = "createddatetime")
    private Long createdDateTime;
    @Column(value = "lastmodifieddatetime")
    private Long lastModifiedDateTime;
}
