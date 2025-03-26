package com.example.middle_roadmap.service.implement;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.dto.authentication.LoginRequest;
import com.example.middle_roadmap.entity.User;
import com.example.middle_roadmap.repository.UserRepository;
import com.example.middle_roadmap.utils.CommonUtils;
import com.example.middle_roadmap.utils.JwtUtil;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Override
    public BaseResponse login(LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByUsername(request.getUsername());
            String sessionId = CommonUtils.generateSessionId(request.getUsername());
            String token = jwtUtil.createToken(authentication, user, sessionId);
            // example for delegate spring security
//                ExecutorService executor = Executors.newSingleThreadExecutor();
//                executor.execute(() -> {
//                    // can take authentication from context in another thread
//                    Authentication authen = SecurityContextHolder.getContext().getAuthentication();
//                    System.out.println(authen.getAuthorities());
//                });
            //
            return BaseResponse.simpleSuccess(token);
        } catch (AuthenticationException ex) {
            throw new CustomRuntimeException("Invalid username or password");
        }
    }
}
