package com.yasas.unitandresolve.service.user.service;

import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> createUser(UserDto userDto);

    Flux<UserDto> findAllUsers();
}
