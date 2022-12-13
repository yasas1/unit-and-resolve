package com.yasas.unitandresolve.service.unit.util;

import com.yasas.unitandresolve.service.unit.entity.Unit;
import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
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
                .build();
    }

    public static UnitDto mapUnitToUnitDto(Unit unit) {
        return UnitDto.builder()
                .id(unit.getId())
                .name(unit.getName())
                .context(unit.getContext())
                .description(unit.getDescription())
                .ownerId(unit.getOwnerId())
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
}
