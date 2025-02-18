package com.example.middle_roadmap.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CustomValidateException extends CustomRuntimeException {

    public CustomValidateException(String message) {
        super(message);
    }
    public CustomValidateException(String message, Map<String, Object> errorMap) {
        super(message, errorMap);
        this.setErrMap(errorMap);
    }

    public CustomValidateException(String message, List errorList) {
        super(message, errorList);
        this.setErrorList(errorList);
    }
}
