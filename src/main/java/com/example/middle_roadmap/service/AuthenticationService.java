package com.example.middle_roadmap.service;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.dto.authentication.LoginRequest;

public interface AuthenticationService {
    BaseResponse login(LoginRequest request);
}
