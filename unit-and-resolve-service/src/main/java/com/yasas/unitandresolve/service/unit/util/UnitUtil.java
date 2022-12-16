package com.yasas.unitandresolve.service.unit.util;

import com.yasas.unitandresolve.service.unit.entity.Unit;
import com.yasas.unitandresolve.service.unit.entity.UserUnit;
import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.entity.dto.UserUnitDto;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnitUtil {
    private UnitUtil() {
    }

    public static Unit mapUnitDtoToUnit(UnitDto unitDto) {
        return Unit.builder()
                .name(unitDto.getName())
                .context(unitDto.getContext())
                .description(unitDto.getDescription())
                .ownerId(unitDto.getOwnerId())
                .createdDateTime(unitDto.getCreatedDateTime())
                .lastModifiedDateTime(unitDto.getLastModifiedDateTime())
                .build();
    }

    public static UnitDto mapUnitToUnitDto(Unit unit, UserDto userDto) {
        return UnitDto.builder()
                .id(unit.getId())
                .name(unit.getName())
                .context(unit.getContext())
                .description(unit.getDescription())
                .ownerId(unit.getOwnerId())
                .createdDateTime(unit.getCreatedDateTime())
                .lastModifiedDateTime(unit.getLastModifiedDateTime())
                .owner(userDto)
                .build();
    }

    public static void validateUnitRequest(UnitDto unitDto) {
        if (unitDto.getName() == null || unitDto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit name is mandatory");
        }
        if (unitDto.getOwnerId() == 0.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit owner is mandatory");
        }
    }

    public static UserUnit mapUserUnitDtoToUserUnit(UserUnitDto userUnitDto) {
        return UserUnit.builder()
                .unitId(userUnitDto.getUnitId())
                .unitId(userUnitDto.getUnitId())
                .isAdmin(userUnitDto.isAdmin())
                .build();
    }

    public static UserUnitDto mapUserUnitToUserUnitDto(UserUnit userUnit, UserDto userDto, UnitDto unitDto) {
        return UserUnitDto.builder()
                .unitId(userUnit.getUnitId())
                .unitId(userUnit.getUnitId())
                .isAdmin(userUnit.isAdmin())
                .user(userDto)
                .unit(unitDto)
                .build();
    }
}
