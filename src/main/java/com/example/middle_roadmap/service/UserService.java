package com.example.middle_roadmap.service;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.entity.Device;
import com.example.middle_roadmap.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    BaseResponse list();
    BaseResponse save(User user);
    BaseResponse delete(long id);
    BaseResponse removeDevice(List<Long> deviceIds);
    BaseResponse addDevice(List<Device> deviceIds, Long userId);
}
