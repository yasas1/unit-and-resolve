package com.yasas.unitandresolve.service.user.service.impl;

import com.yasas.unitandresolve.service.common.CommonUtil;
import com.yasas.unitandresolve.service.common.EmailService;
import com.yasas.unitandresolve.service.common.ResponseMessage;
import com.yasas.unitandresolve.service.user.entity.User;
import com.yasas.unitandresolve.service.user.entity.VerificationCode;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.repository.UserRepository;
import com.yasas.unitandresolve.service.user.repository.VerificationCodeRepository;
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
    private final VerificationCodeRepository verificationCodeRepository;

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
    public Flux<UserDto> findAllUsers() {
        return userRepository.findAll().map(UserUtil::mapUserToUserDto);
    }

    @Override
    public Mono<ResponseMessage> emailVerify(String email, String otp) {
        return verificationCodeRepository.findByEmail(email)
                .map(verificationCode -> {
                    if (verificationCode.getExpirationTime() < System.currentTimeMillis()) {
                        throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Your otp is expired. Try again");
                    }
                    if (verificationCode.getCode().equals(otp)) {
                        return new ResponseMessage("Proceed to Registration", 200);
                    }
                    throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Given OTP is not valid. Please enter a valid OTP.");
                });
    }

    @Transactional
    @Override
    public Mono<ResponseMessage> proceedWithEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new ResponseMessage("Proceed to Login", 200))
                .switchIfEmpty(Mono.defer(()-> sendEmailWithOTP(email)));
    }

    private Mono<ResponseMessage> sendEmailWithOTP(String toEmail) {
        if (!UserUtil.isValidEmail(toEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email format");
        }
        String otp = CommonUtil.generateCode(4);
        return createOrUpdateUserVerification(toEmail, otp)
                .then(Mono.justOrEmpty(emailService.sendEmail(toEmail, "UnR-Email Verification", "Your verification code is " + otp))
                        .map(verificationCode -> new ResponseMessage("Verify Your Email", 204)));
    }

    private Mono<VerificationCode> createOrUpdateUserVerification(String toEmail, String otp) {
        return verificationCodeRepository.findByEmail(toEmail)
                .flatMap(verificationCode -> verificationCodeRepository.save(VerificationCode.builder()
                        .id(verificationCode.getId()).code(otp).userEmail(toEmail).expirationTime(System.currentTimeMillis() + 3600000).createdDateTime(System.currentTimeMillis()).build()))
                .switchIfEmpty(Mono.defer(() -> verificationCodeRepository.save(VerificationCode.builder()
                        .code(otp).userEmail(toEmail).expirationTime(System.currentTimeMillis() + 3600000).createdDateTime(System.currentTimeMillis()).build())));
    }

}
