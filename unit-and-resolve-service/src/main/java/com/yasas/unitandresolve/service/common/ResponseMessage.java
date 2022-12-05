package com.yasas.unitandresolve.service.common;

import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseMessage {
    private String message;
    private int status;
}
