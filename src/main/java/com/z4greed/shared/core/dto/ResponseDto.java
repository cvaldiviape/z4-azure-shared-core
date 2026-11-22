package com.z4greed.shared.core.dto;

import lombok.Builder;

// Forma estándar de todas las respuestas JSON de los servicios Z4.
@Builder
public record ResponseDto<T>(
    String code,
    Integer statusCode,
    String message,
    T data) {}
