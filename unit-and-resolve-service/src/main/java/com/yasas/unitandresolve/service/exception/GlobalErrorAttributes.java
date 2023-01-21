package com.yasas.unitandresolve.service.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = getError(request);
        errorMap.put("message", error instanceof ResponseStatusException ? ((ResponseStatusException)error).getReason() : error.getMessage());
        errorMap.put("path", request.path());
        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations.from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
        HttpStatus errorStatus = error instanceof ResponseStatusException ? ((ResponseStatusException)error).getStatus() : (HttpStatus)responseStatusAnnotation.getValue("code", HttpStatus.class)
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
        errorMap.put("status", errorStatus.value());
        errorMap.put("error", errorStatus.getReasonPhrase());
        return errorMap;
    }
}
