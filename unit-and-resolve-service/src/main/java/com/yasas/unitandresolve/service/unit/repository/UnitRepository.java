package com.yasas.unitandresolve.service.unit.repository;

import com.yasas.unitandresolve.service.unit.entity.Unit;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UnitRepository extends ReactiveCrudRepository<Unit, Long> {
    Mono<Unit> findByName(String name);
    Flux<Unit> findAll(Sort sort);
    @Query("SELECT * FROM public.unit WHERE name = :name AND ownerid = :ownerId ORDER BY lastmodifieddatetime DESC LIMIT 1")
    Mono<Unit> findByNameAndOwnerId(String name, Long ownerId);
}
