package com.yasas.unitandresolve.service.user.service.impl;

import com.yasas.unitandresolve.service.common.CommonUtil;
import com.yasas.unitandresolve.service.common.EmailService;
import com.yasas.unitandresolve.service.common.ResponseMessage;
import com.yasas.unitandresolve.service.user.entity.User;
import com.yasas.unitandresolve.service.user.entity.VerificationCode;
import com.yasas.unitandresolve.service.user.entity.dto.UserCreateRequest;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import com.yasas.unitandresolve.service.user.repository.UserRepository;
import com.yasas.unitandresolve.service.user.repository.VerificationCodeRepository;
import com.yasas.unitandresolve.service.user.service.UserService;
import com.yasas.unitandresolve.service.user.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VerificationCodeRepository verificationCodeRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Transactional
    @Override
    public Mono<Object> createUser(UserCreateRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(userExist -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your email is already registered")))
                .switchIfEmpty(Mono.defer(() -> saveUserIfValid(request).map(UserUtil::mapUserToUserDto)))
                .doOnError(Throwable::printStackTrace);
    }

    private Mono<User> saveUserIfValid(UserCreateRequest request) {
        UserUtil.validateUserCreateRequest(request);
        User user = UserUtil.mapUserRequestToUser(request);
        user.setCreatedDateTime(System.currentTimeMillis());
        user.setLastModifiedDateTime(System.currentTimeMillis());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getProfilePicBase64() != null && !request.getProfilePicBase64().isBlank()) {
            user.setProfilePicBase64(request.getProfilePicBase64());
        } else {
            log.info("default profile picture is set to {}" , request.getEmail());
            //set default pro pic
        }
        return userRepository.save(user);
    }

    @Override
    public Mono<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserUtil::mapUserToUserDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Flux<UserDto> findAllUsers() {
        return userRepository.findAll().map(UserUtil::mapUserToUserDto).doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<ResponseMessage> emailVerify(String email, String otp) {
        return verificationCodeRepository.findByEmail(email)
                .map(verificationCode -> {
                    if (verificationCode.getExpirationTime() < System.currentTimeMillis()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your otp is expired. Try again.");
                    }
                    if (verificationCode.getCode().equals(otp)) {
                        return new ResponseMessage("Proceed to Registration", 200);
                    }
                    throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Given OTP is not valid. Please enter a valid OTP.");
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given OTP is not valid. Please enter a valid OTP.")))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Mono<UserDto> userLogin(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return UserUtil.mapUserToUserDto(user);
                    } else {
                        throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Credentials do not match with the record");
                    }
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")))
                .doOnError(Throwable::printStackTrace);
    }

    @Transactional
    @Override
    public Mono<ResponseMessage> proceedWithEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new ResponseMessage("Proceed to Login", 200))
                .switchIfEmpty(Mono.defer(()-> sendEmailWithOTP(email)))
                .doOnError(Throwable::printStackTrace);
    }

    private Mono<ResponseMessage> sendEmailWithOTP(String toEmail) {
        if (!UserUtil.isValidEmail(toEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email format");
        }
        String otp = CommonUtil.generateCode(4);
        return createOrUpdateUserVerification(toEmail, otp)
                .then(Mono.justOrEmpty(emailService.sendEmail(toEmail, "UnR-Email Verification", "Your verification code is " + otp))
                        .doOnNext(email -> log.info("Verification email was sent to {} with otp {}", toEmail, otp))
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
