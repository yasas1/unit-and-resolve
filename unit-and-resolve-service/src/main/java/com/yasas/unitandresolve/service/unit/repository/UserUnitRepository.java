package com.yasas.unitandresolve.service.unit.repository;

import com.yasas.unitandresolve.service.unit.entity.UserUnit;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserUnitRepository extends ReactiveCrudRepository<UserUnit, Long> {
    @Query("SELECT * FROM public.user_unit WHERE userid = :userId ORDER BY id ASC")
    Flux<UserUnit> findByUserId(Long userId);
    @Query("SELECT * FROM public.user_unit WHERE unitid = :unitId ORDER BY id ASC")
    Flux<UserUnit> findByUnitId(Long unitId);
}
