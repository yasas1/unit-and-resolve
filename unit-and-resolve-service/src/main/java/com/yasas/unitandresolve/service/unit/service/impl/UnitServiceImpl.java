package com.yasas.unitandresolve.service.unit.service.impl;

import com.yasas.unitandresolve.service.unit.entity.Unit;
import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.repository.UnitRepository;
import com.yasas.unitandresolve.service.unit.service.UnitService;
import com.yasas.unitandresolve.service.unit.util.UnitUtil;
import com.yasas.unitandresolve.service.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("id"));

    @Override
    public Mono<UnitDto> createUnit(UnitDto unitDto) {
        UnitUtil.validateUnitRequest(unitDto);
        unitDto.setCreatedDateTime(System.currentTimeMillis());
        unitDto.setLastModifiedDateTime(System.currentTimeMillis());
        return userRepository.findById(unitDto.getOwnerId())
                .flatMap(user -> unitRepository.save(UnitUtil.mapUnitDtoToUnit(unitDto)).flatMap(this::loadOwner)
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
                                    Unit newUnit = UnitUtil.mapUnitDtoToUnit(unitDto);
                                    newUnit.setId(foundUnit.getId());
                                    newUnit.setCreatedDateTime(foundUnit.getCreatedDateTime());
                                    newUnit.setLastModifiedDateTime(System.currentTimeMillis());
                                    return unitRepository.save(newUnit)
                                            .flatMap(this::loadOwner)
                                            .map(UnitUtil::mapUnitToUnitDto);
                                })
                                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit is not found")))

                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner is not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<UnitDto> getUnitById(Long id) {
        return unitRepository.findById(id).flatMap(this::loadOwner).map(UnitUtil::mapUnitToUnitDto);
    }

    @Override
    public Mono<UnitDto> getUnitByName(String name) {
        return unitRepository.findByName(name).flatMap(this::loadOwner).map(UnitUtil::mapUnitToUnitDto);
    }

    @Override
    public Flux<UnitDto> getAllUnits() {
        return unitRepository.findAll(DEFAULT_SORT).flatMap(this::loadOwner).map(UnitUtil::mapUnitToUnitDto);
    }

    private Mono<Unit> loadOwner(final Unit unit) {
        return userRepository.findById(unit.getOwnerId())
                .doOnNext(unit::setOwner)
                .thenReturn(unit);
    }

}
