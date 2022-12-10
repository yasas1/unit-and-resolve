package com.yasas.unitandresolve.service.user.service;

import com.yasas.unitandresolve.service.common.ResponseMessage;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> createUser(UserDto userDto);

    Flux<UserDto> findAllUsers();

    Mono<UserDto> findByEmail(String email);

    Mono<ResponseMessage> proceedWithEmail(String email);

    Mono<ResponseMessage> emailVerify(String email, String otp);
}
