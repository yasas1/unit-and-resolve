package com.yasas.unitandresolve.service.user.repository;

import com.yasas.unitandresolve.service.user.entity.VerificationCode;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VerificationCodeRepository extends ReactiveCrudRepository<VerificationCode, String> {
    @Query("SELECT * FROM public.verification WHERE userEmail = :emai ORDER BY createddatetime  DESC LIMIT 1")
    Mono<VerificationCode> findByIdAndUserEmail(String email);
}
