package com.yasas.unitandresolve.service.unit.service;

import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UnitService {
    Mono<UnitDto> createUnit(UnitDto unitDto);
    Mono<UnitDto> updateUnit(UnitDto unitDto);
    Mono<UnitDto> getUnitById(Long unitId);
    Mono<UnitDto> getUnitByName(String name);
    Flux<UnitDto> getAllUnits();
    Flux<UnitDto> getAllMyUnits(long userId);
}
