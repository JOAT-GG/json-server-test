package com.example.demo.exception;

import lombok.*;
import org.springframework.http.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST */
    ACCESS_DENIED_ERROR(HttpStatus.UNAUTHORIZED, "접근이 허용되지 않습니다."),
    FILED_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "필드값이 유효하지 않습니다."),
    INVALID_GAMENAME_OR_TAG(HttpStatus.BAD_REQUEST, "유요하지 않은 게임 이름 or 태그입니다.");

    /* 404 NOT FOUND : Resource 를 찾을 수 없음 */

    private final HttpStatus httpStatus;
    private final String detail; // %s 가 포함된 detail
}