package com.yasas.unitandresolve.service.user.controller;

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
}
