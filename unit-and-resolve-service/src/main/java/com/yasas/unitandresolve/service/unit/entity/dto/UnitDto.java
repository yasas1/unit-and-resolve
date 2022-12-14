package com.yasas.unitandresolve.service.unit.entity.dto;

import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto {
    private long id;
    @NotBlank(message = "Unit name id is required")
    @NotEmpty(message = "Unit name id is required")
    private String name;
    private String context;
    private String description;
    private long ownerId;
    private Long createdDateTime;
    private Long lastModifiedDateTime;
    private UserDto owner;
}
