package com.yasas.unitandresolve.service.unit.service;

import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.entity.dto.UserUnitDto;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserUnitService {
    Mono<UserUnitDto> assignUserToUnit(UserUnitDto userUnitDto);
    Flux<UserUnitDto> findAllUserUnits();

    Flux<UserDto> findAssignedAllUserToUnitByUnitId(long unitId);
    Flux<UnitDto> findAssignedAllUnitsToUserByUserId(long userId);

}
