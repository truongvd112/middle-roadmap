package com.example.middle_roadmap.repository;

import com.example.middle_roadmap.dto.user.UserDto;

import java.util.List;

public interface CustomUserRepository{
    List<UserDto> findAllByRoleName(String roleName);
}
