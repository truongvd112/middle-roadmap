package com.example.middle_roadmap.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class Constants {
    public static final class ROLE {
        public static final String ROLE_ADMIN = "admin";
    }

    @Getter
    public enum ErrorCode {
        /* 400 BAD_REQUEST */
        INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid Parameter"),

        /* 401 Unauthorized */
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
        /* 404 Not Found */
        NOT_FOUND(HttpStatus.NOT_FOUND, "Not found"),

        VALIDATE(HttpStatus.UNPROCESSABLE_ENTITY, "Validate exception"),
        /* 403 Permission denied */
        PERMISSION(HttpStatus.FORBIDDEN, "Permission exception"),
        /* 500 Internal Server Error */
        SYSTEM_OTHER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Other System error"),
        /* 503 Service Unavailable */
        SYSTEM_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "서버 문제로 인한 서비스 불가");

        private final HttpStatus httpStatus;
        private final String errorMessage;

        ErrorCode(HttpStatus httpStatus, String errorMessage) {
            this.httpStatus = httpStatus;
            this.errorMessage = errorMessage;
        }
    }
}
