package com.yasas.unitandresolve.service.user.service.impl;

import com.yasas.unitandresolve.service.common.CommonUtil;
import com.yasas.unitandresolve.service.common.EmailService;
import com.yasas.unitandresolve.service.common.ResponseMessage;
import com.yasas.unitandresolve.service.user.entity.User;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.repository.UserRepository;
import com.yasas.unitandresolve.service.user.service.UserService;
import com.yasas.unitandresolve.service.user.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Transactional
    @Override
    public Mono<UserDto> createUser(UserDto userDto) {
        UserUtil.validateUserCreateRequest(userDto);
        User user = UserUtil.mapUserDtoToUser(userDto);
        user.setCreatedDateTime(System.currentTimeMillis());
        user.setLastModifiedDateTime(System.currentTimeMillis());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getProfilePicBase64() != null && !userDto.getProfilePicBase64().isBlank()) {
            user.setProfilePicBase64(userDto.getProfilePicBase64());
        } else {
            //set default pro pic
        }
        System.out.println(user);
        return userRepository.save(user).map(UserUtil::mapUserToUserDto);
    }

    @Override
    public Mono<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserUtil::mapUserToUserDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

    @Override
    public Mono<ResponseMessage> proceedWithEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new ResponseMessage("Proceed to Login", 200))
                .switchIfEmpty(Mono.defer(()-> Mono.just(sendEmailWithOTP(email))));
    }

    private ResponseMessage sendEmailWithOTP(String toEmail) {
        String otp = CommonUtil.generateCode(4);
        String message = "Your verification code is " + otp;
        emailService.sendEmail(toEmail, "UnR-Email Verification", message);
        return new ResponseMessage("Verify Your Email", 204);
    }

    @Override
    public Flux<UserDto> findAllUsers() {
        return userRepository.findAll().map(UserUtil::mapUserToUserDto);
    }
}
