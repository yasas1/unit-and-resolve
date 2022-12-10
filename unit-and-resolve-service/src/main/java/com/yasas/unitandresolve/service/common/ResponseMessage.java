package com.yasas.unitandresolve.service.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseMessage {
    private String message;
    private int status;
}
