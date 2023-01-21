package com.yasas.unitandresolve.service.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = super.getErrorAttributes(request, options);
        Throwable error = getError(request);
        errorMap.put("message", error instanceof ResponseStatusException ? ((ResponseStatusException)error).getReason() : error.getMessage());
        return errorMap;
    }
}
