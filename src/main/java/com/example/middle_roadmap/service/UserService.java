package com.example.middle_roadmap.service;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.entity.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    BaseResponse list();
    BaseResponse save(User user);
    BaseResponse delete(long id);
    BaseResponse threadExample();
    BaseResponse bankTransfer(long userId1, long userId2);
}
