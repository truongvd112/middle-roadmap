package com.example.middle_roadmap.dto.authentication;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginRequest {
    @NotNull
    @Length(max = 30)
    private String username;
    @NotNull
    @Length(max = 30)
    private String password;
}
