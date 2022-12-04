package com.yasas.unitandresolve.service.user.repository;

import com.yasas.unitandresolve.service.user.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Long> {
    @Query("SELECT * FROM public.user WHERE email = :email ORDER BY lastmodifieddatetime DESC LIMIT 1")
    Mono<User> findByEmail(String email);
}
