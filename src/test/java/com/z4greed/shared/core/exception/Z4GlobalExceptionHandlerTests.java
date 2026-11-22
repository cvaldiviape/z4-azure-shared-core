package com.z4greed.shared.core.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.z4greed.shared.core.dto.ResponseDto;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class Z4GlobalExceptionHandlerTests {
    private final Z4GlobalExceptionHandler handler = new Z4GlobalExceptionHandler();

    @Getter
    @RequiredArgsConstructor
    enum SampleErrorCode implements ErrorCode {
        ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found");

        private final HttpStatus httpStatus;
        private final String message;
    }

    @Test
    void shouldUseTheStatusAndCodeOfTheServiceErrorCode() {
        ResponseEntity<ResponseDto<Void>> response = this.handler.handleBusinessException(new BusinessException(SampleErrorCode.ORDER_NOT_FOUND));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("ORDER_NOT_FOUND");
        assertThat(response.getBody().statusCode()).isEqualTo(404);
        assertThat(response.getBody().message()).isEqualTo("Order not found");
    }

    @Test
    void shouldReturnTheFirstErrorOfEachInvalidField() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", "email", "must not be blank"));
        bindingResult.addError(new FieldError("request", "email", "must be a well-formed email"));
        bindingResult.addError(new FieldError("request", "password", null));
        MethodParameter parameter = new MethodParameter(Object.class.getMethod("equals", Object.class), 0);

        ResponseEntity<ResponseDto<Map<String, String>>> response =
                this.handler.handleValidationException(new MethodArgumentNotValidException(parameter, bindingResult));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo("VALIDATION_ERROR");
        assertThat(response.getBody().data())
                .containsEntry("email", "must not be blank")
                .containsEntry("password", "Invalid value");
    }
}
