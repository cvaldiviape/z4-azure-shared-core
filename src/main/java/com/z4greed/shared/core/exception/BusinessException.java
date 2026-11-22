package com.z4greed.shared.core.exception;

import lombok.Getter;

// Error de negocio esperado (pedido no encontrado, credenciales inválidas...).
// Z4GlobalExceptionHandler lo convierte en una respuesta con el estado HTTP de su ErrorCode.
@Getter
public class BusinessException extends RuntimeException {
    private final transient ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
