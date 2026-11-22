package com.z4greed.shared.core.exception;

import org.springframework.http.HttpStatus;

// Contrato que implementa el ErrorCodeEnum de cada servicio.
// Un enum con @Getter (httpStatus, message) ya lo cumple: name() lo aporta Java.
public interface ErrorCode {
    String name();

    HttpStatus getHttpStatus();

    String getMessage();
}
