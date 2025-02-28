package com.example.middle_roadmap.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.example.middle_roadmap.utils.Constants.ROLE.ROLE_ADMIN;

@Service
public class UserAuthorizeService {
    public boolean hasRole(String... roles) {
        List<String> roleList = Arrays.asList(roles);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> {
                    String authority = grantedAuthority.getAuthority();
                    return StringUtils.isNotBlank(authority) &&
                            (roleList.contains(authority) || ROLE_ADMIN.equals(authority));
                });
    }
}
