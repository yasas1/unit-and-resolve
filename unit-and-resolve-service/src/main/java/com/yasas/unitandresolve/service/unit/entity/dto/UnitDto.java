package com.yasas.unitandresolve.service.unit.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class UnitDto {
    private long id;
    private String name;
    private String context;
    private String description;
    private long ownerId;
    private Long createdDateTime;
    private Long lastModifiedDateTime;
}
