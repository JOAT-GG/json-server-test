package com.example.demo.exception.handler;

import com.example.demo.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    protected ResponseEntity<ErrorResponse> catchException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage(), e.toString()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("커스템 에러 핸들링 : " + e.getMessage());
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.fromErrorCodeToResponse(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleArgNotValidException(MethodArgumentNotValidException e) {
        log.error("필드 에러 핸들링 : " + e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.fromErrorCodeToResponse(ErrorCode.FILED_VALIDATION_FAILED));
    }

}
