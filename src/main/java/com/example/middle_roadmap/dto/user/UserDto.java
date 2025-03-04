package com.example.middle_roadmap.dto.user;

import com.example.middle_roadmap.dto.device.DeviceDto;
import com.example.middle_roadmap.dto.role.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private List<DeviceDto> devices;
    private RoleDto role;
}
