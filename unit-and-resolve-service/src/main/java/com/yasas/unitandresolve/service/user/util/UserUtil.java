package com.yasas.unitandresolve.service.user.util;

import com.yasas.unitandresolve.service.user.entity.User;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserUtil {
    private UserUtil() {
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

    public static void validateUserCreateRequest(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getFirstName() == null || userDto.getLastName() == null || userDto.getPassword() == null ||
                userDto.getEmail().isBlank() || userDto.getFirstName().isBlank() || userDto.getLastName().isBlank() || userDto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad user create request");
        }
        if (!isStrong(userDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your password is not strong");
        }
    }

    public static boolean isStrong(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!\\-@%#&()_[{}]:;',?\\\\|/\".*~`$^+=<>])[A-Za-z\\d!\\-@%#&()_[{}]:;',?\\\\|/\".*~`$^+=<>]{8,}$");
    }

}
