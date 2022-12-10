package com.yasas.unitandresolve.service.user.util;

import com.yasas.unitandresolve.service.user.entity.User;
import com.yasas.unitandresolve.service.user.entity.dto.UserCreateRequest;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserUtil {
    private UserUtil() {
    }

    public static User mapUserRequestToUser(UserCreateRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    public static User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }

    public static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicBase64(user.getProfilePicBase64())
                .createdDateTime(user.getCreatedDateTime())
                .lastModifiedDateTime(user.getLastModifiedDateTime())
                .build();
    }

    public static void validateUserCreateRequest(UserCreateRequest request) {
        if (request.getEmail() == null || request.getFirstName() == null || request.getLastName() == null || request.getPassword() == null ||
                request.getEmail().isBlank() || request.getFirstName().isBlank() || request.getLastName().isBlank() || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad user create request");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Email format");
        }
        if (!isStrong(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your password is not strong");
        }
    }

    public static boolean isStrong(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!\\-@%#&()_[{}]:;',?\\\\|/\".*~`$^+=<>])[A-Za-z\\d!\\-@%#&()_[{}]:;',?\\\\|/\".*~`$^+=<>]{8,}$");
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
