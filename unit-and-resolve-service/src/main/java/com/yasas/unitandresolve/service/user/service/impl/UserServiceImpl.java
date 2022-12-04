package com.yasas.unitandresolve.service.user.service.impl;

import com.yasas.unitandresolve.service.user.entity.User;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.repository.UserRepository;
import com.yasas.unitandresolve.service.user.service.UserService;
import com.yasas.unitandresolve.service.user.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Mono<UserDto> createUser(UserDto userDto) {
        UserUtil.validateUserCreateRequest(userDto);
        User user = UserUtil.mapUserDtoToUser(userDto);
        user.setCreatedDateTime(System.currentTimeMillis());
        user.setLastModifiedDateTime(System.currentTimeMillis());
        if(userDto.getProfilePicBase64() != null && userDto.getProfilePicBase64().isBlank()){
            user.setProfilePicBase64(userDto.getProfilePicBase64());
        } else {
            //set default pro pic
        }
        System.out.println(user);
        return userRepository.save(user).map(UserUtil::mapUserToUserDto);
    }

    @Override
    public Flux<UserDto> findAllUsers() {
        return userRepository.findAll().map(UserUtil::mapUserToUserDto);
    }
}
