package com.yasas.unitandresolve.service.user.controller;

import com.yasas.unitandresolve.service.common.ResponseMessage;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public Mono<UserDto> createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping()
    public Flux<UserDto> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{email}")
    public Mono<UserDto> findByEmail(@PathVariable(name = "email") String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/{email}/proceed")
    public Mono<ResponseMessage> proceedWithEmail(@PathVariable(name = "email") String email){
        return userService.proceedWithEmail(email);
    }

    @GetMapping("/{email}/verify")
    public Mono<ResponseMessage> emailVerify(@PathVariable(name = "email") String email, @RequestParam String otp){
        return userService.emailVerify(email, otp);
    }
}
