package com.yasas.unitandresolve.service.unit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "user_unit")
public class UserUnit {
    @Id
    private long id;
    @Column(value = "userid")
    private long userId;
    @Column(value = "unitid")
    private long unitId;
    @Column(value = "isadmin")
    private boolean isAdmin;
}
