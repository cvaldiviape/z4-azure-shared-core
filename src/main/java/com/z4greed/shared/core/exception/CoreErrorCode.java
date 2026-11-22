package com.z4greed.shared.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

// Errores comunes a todos los servicios. Los errores de negocio viven en el ErrorCodeEnum de cada servicio.
@Getter
@RequiredArgsConstructor
public enum CoreErrorCode implements ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Request validation failed");

    private final HttpStatus httpStatus;
    private final String message;
}
