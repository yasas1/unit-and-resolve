package com.yasas.unitandresolve.service.unit.service.impl;

import com.yasas.unitandresolve.service.unit.entity.UserUnit;
import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.entity.dto.UserUnitDto;
import com.yasas.unitandresolve.service.unit.repository.UserUnitRepository;
import com.yasas.unitandresolve.service.unit.service.UnitService;
import com.yasas.unitandresolve.service.unit.service.UserUnitService;
import com.yasas.unitandresolve.service.unit.util.UnitUtil;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class UserUnitServiceImpl implements UserUnitService {

    private final UserUnitRepository userUnitRepository;
    private final UserService userService;
    private final UnitService unitService;

    @Override
    public Mono<UserUnitDto> assignUserToUnit(UserUnitDto userUnitDto) {
        return unitService.getUnitById(userUnitDto.getUnitId())
                .doOnNext(unitDto -> log.info("unit to be assigned {}", unitDto))
                .flatMap(unitDto ->
                        userService.findUserById(userUnitDto.getUserId())
                                .doOnNext(userDto -> {
                                    unitDto.setOwner(userDto);
                                    log.info("user to assign {}", userDto);
                                })
                                .flatMap(userDto -> userUnitRepository.save(UnitUtil.mapUserUnitDtoToUserUnit(userUnitDto))
                                        .map(userUnit -> UnitUtil.mapUserUnitToUserUnitDto(userUnit, userDto, unitDto)))
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not found")))
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit is not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Flux<UserUnitDto> findAllUserUnits() {
        return userUnitRepository.findAll().map(userUnit -> UnitUtil.mapUserUnitToUserUnitDto(userUnit, null, null));
    }

    @Override
    public Flux<UserDto> findAssignedAllUserToUnitByUnitId(long unitId) {
        return userUnitRepository.findByUnitId(unitId)
                .map(UserUnit::getUserId)
                .flatMap(userService::findUserById);
    }

    @Override
    public Flux<UnitDto> findAssignedAllUnitsToUserByUserId(long userId) {
        return userUnitRepository.findByUserId(userId)
                .map(UserUnit::getUnitId)
                .flatMap(unitService::getUnitById);
    }
}
