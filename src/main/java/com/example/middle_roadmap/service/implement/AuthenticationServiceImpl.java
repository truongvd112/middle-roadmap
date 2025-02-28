package com.example.middle_roadmap.service.implement;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.dto.authentication.LoginRequest;
import org.springframework.security.core.GrantedAuthority;
import com.example.middle_roadmap.exception.CustomRuntimeException;
import com.example.middle_roadmap.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final Map<String, String> userMap = new HashMap<>();
    @Override
    public BaseResponse login(LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userMap.put("currentUser", request.getUsername());
            userMap.put("currentRole", getCurrentRoleFromAuthentication());
            return BaseResponse.simpleSuccess("success");
        } catch (AuthenticationException ex) {
            throw new CustomRuntimeException("Invalid username or password");
        }
    }

    @Override
    public String getCurrentUser() {
        return userMap.get("currentUser");
    }

    @Override
    public String getCurrentRole() {
        return userMap.get("currentRole");
    }

    private String getCurrentRoleFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .findFirst()
                .orElse(null);
    }
}
