package com.example.middle_roadmap.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAuthorizedException extends CustomRuntimeException {

    public CustomAuthorizedException(String message) {
        super(message);
    }
}
