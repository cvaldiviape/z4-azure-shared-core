package com.z4greed.shared.core.exception;

import com.z4greed.shared.core.dto.ResponseDto;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Convierte las excepciones comunes en respuestas ResponseDto.
// Tiene la menor prioridad: si un servicio define su propio @RestControllerAdvice para un caso, el del servicio gana.
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class Z4GlobalExceptionHandler {
    private static final String INVALID_VALUE = "Invalid value";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> mapValidationErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> mapValidationErrors.putIfAbsent(
                fieldError.getField(),
                Objects.requireNonNullElse(fieldError.getDefaultMessage(), INVALID_VALUE)));

        return toResponse(CoreErrorCode.VALIDATION_ERROR, CoreErrorCode.VALIDATION_ERROR.getMessage(), mapValidationErrors);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto<Void>> handleBusinessException(BusinessException exception) {
        return toResponse(exception.getErrorCode(), exception.getMessage(), null);
    }

    private static <T> ResponseEntity<ResponseDto<T>> toResponse(ErrorCode errorCode, String message, T data) {
        ResponseDto<T> responseDto = ResponseDto.<T>builder()
                .code(errorCode.name())
                .statusCode(errorCode.getHttpStatus().value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(responseDto);
    }
}
