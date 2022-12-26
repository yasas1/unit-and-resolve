package com.yasas.unitandresolve.service.unit.entity.dto;

import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserUnitDto {
    private long id;
    private long userId;
    private long unitId;
    private boolean isAdmin;
    private UserDto user;
    private UnitDto unit;
    private Long createdDateTime;
    private Long lastModifiedDateTime;
}
