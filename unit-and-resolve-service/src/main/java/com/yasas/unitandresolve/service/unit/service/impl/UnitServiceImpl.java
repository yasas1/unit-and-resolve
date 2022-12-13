package com.yasas.unitandresolve.service.unit.service.impl;

import com.yasas.unitandresolve.service.unit.entity.Unit;
import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.repository.UnitRepository;
import com.yasas.unitandresolve.service.unit.service.UnitService;
import com.yasas.unitandresolve.service.unit.util.UnitUtil;
import com.yasas.unitandresolve.service.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<UnitDto> createUnit(UnitDto unitDto) {
        UnitUtil.validateUnitRequest(unitDto);
        Unit unit = UnitUtil.mapUnitDtoToUnit(unitDto);
        unit.setCreatedDateTime(System.currentTimeMillis());
        unit.setLastModifiedDateTime(System.currentTimeMillis());
        return userRepository.findById(unitDto.getOwnerId())
                .flatMap(user -> unitRepository.save(unit)
                .map(UnitUtil::mapUnitToUnitDto))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner is not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<UnitDto> updateUnit(UnitDto unitDto) {
        UnitUtil.validateUnitRequest(unitDto);
        return userRepository.findById(unitDto.getOwnerId())
                .flatMap(user ->
                        unitRepository.findById(unitDto.getId())
                                .flatMap(foundUnit -> {
                                    unitDto.setCreatedDateTime(foundUnit.getCreatedDateTime());
                                    unitDto.setLastModifiedDateTime(System.currentTimeMillis());
                                    return unitRepository.save(UnitUtil.mapUnitDtoToUnit(unitDto))
                                            .map(UnitUtil::mapUnitToUnitDto);
                                })
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit is not found")))

                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner is not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<UnitDto> getUnitById(Long id) {
        return unitRepository.findById(id).map(UnitUtil::mapUnitToUnitDto);
    }

    @Override
    public Mono<UnitDto> getUnitByName(String name) {
        return unitRepository.findByName(name).map(UnitUtil::mapUnitToUnitDto);
    }

    @Override
    public Flux<UnitDto> getAllUnits() {
        return unitRepository.findAll().map(UnitUtil::mapUnitToUnitDto);
    }
}
