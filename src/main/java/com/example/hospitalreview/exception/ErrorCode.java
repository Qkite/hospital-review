package com.example.hospitalreview.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name이 중복됩니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error가 발생했습니다."),
    USER_NOT_FOUNDED(HttpStatus.NOT_FOUND, "User를 찾지 못했습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다.")
    ;

    private HttpStatus httpStatus;
    private String message;

}


