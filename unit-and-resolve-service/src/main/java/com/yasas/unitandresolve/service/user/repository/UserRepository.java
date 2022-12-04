package com.yasas.unitandresolve.service.user.repository;

import com.yasas.unitandresolve.service.user.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Long> {

}
