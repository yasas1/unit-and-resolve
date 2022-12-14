package com.yasas.unitandresolve.service.unit.entity;

import com.yasas.unitandresolve.service.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "public.unit")
public class Unit {

    @Id
    private long id;
    @Column(value = "name")
    private String name;
    @Column(value = "context")
    private String context;
    @Column(value = "description")
    private String description;
    @Column(value = "ownerid")
    private long ownerId;
    @Column(value = "createddatetime")
    private Long createdDateTime;
    @Column(value = "lastmodifieddatetime")
    private Long lastModifiedDateTime;

    @Transient
    private User owner;
}
