package com.example.middle_roadmap.exception;


import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(HttpServletRequest request, Exception ex) {
        log.error("Exception: ", ex);
        return this.toResponseEntity(Constants.ErrorCode.SYSTEM_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(CustomValidateException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(CustomValidateException ex) {
        return this.toResponseEntity(Constants.ErrorCode.VALIDATE, ex.getMessage());
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(CustomRuntimeException ex) {
        return this.toResponseEntity(Constants.ErrorCode.SYSTEM_OTHER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(CustomAuthorizedException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(CustomAuthorizedException ex) {
        return this.toResponseEntity(Constants.ErrorCode.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<BaseResponse> handleHttpServerErrorException(HttpServletRequest request, HttpServerErrorException ex) {
        return this.toResponseEntity(Constants.ErrorCode.SYSTEM_OTHER_ERROR, ex.getMessage());
    }



    private ResponseEntity<BaseResponse> toResponseEntity(Constants.ErrorCode errorCode, String errorMessage) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(BaseResponse.builder()
                        .isError(true)
                        .message(errorMessage)
                        .build()
                );
    }

}
