package com.example.middle_roadmap.service.implement;

import com.example.middle_roadmap.dto.BaseResponse;
import com.example.middle_roadmap.dto.DataListResponse;
import com.example.middle_roadmap.entity.Device;
import com.example.middle_roadmap.entity.User;
import com.example.middle_roadmap.repository.DeviceRepository;
import com.example.middle_roadmap.repository.UserRepository;
import com.example.middle_roadmap.service.AuthenticationService;
import com.example.middle_roadmap.service.CurrentUserService;
import com.example.middle_roadmap.service.UserService;
import com.example.middle_roadmap.utils.Constants;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final CurrentUserService currentUserService;
    private final AuthenticationService authenticationService;
    private final RedisTemplate<String, Object> redisTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BaseResponse list() {
        List<User> users = userRepository.findAll();
        return DataListResponse.builder().dataList(users).build();
    }

    @Override
    public BaseResponse save(User user) {
        userRepository.save(user);
        return BaseResponse.simpleSuccess("success");
    }

    @Override
    public BaseResponse delete(long id) {
        userRepository.deleteById(String.valueOf(id));
        return BaseResponse.simpleSuccess("success");
    }

    @Override
    public BaseResponse removeDevice(List<Long> deviceIds) {
//        String roles = currentUserService.getRoles();
        String roles = authenticationService.getCurrentRole();
        if(roles.contains(Constants.ROLE.ROLE_ADMIN)){
            deviceRepository.deleteByIdIn(deviceIds);
            return BaseResponse.simpleSuccess("success");
        } else {
            User user = userRepository.findByUsername(authenticationService.getCurrentUser());
            List<Long> ids = user.getDevices().stream().map(Device::getId).collect(Collectors.toList());
            if(new HashSet<>(ids).containsAll(deviceIds)){
                deviceRepository.deleteByIdIn(deviceIds);
                return BaseResponse.simpleSuccess("success");
            } else {
                return BaseResponse.simpleFailed("can not delete other users' devices");
            }
        }
    }

    @Override
    public BaseResponse addDevice(List<Device> devices, Long userId) {
        User user;
        if (userId == null) {
            user = userRepository.findByUsername(authenticationService.getCurrentUser());
        } else {
            user = userRepository.findById(String.valueOf(userId)).orElse(null);
        }
        if (user != null) {
            user.getDevices().addAll(devices);
            userRepository.save(user);
            return BaseResponse.simpleSuccess("success");
        }
        return BaseResponse.simpleFailed("userId does not exist");
    }

    private List<User> NPlus1Problem() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            // each call will take one more query
            System.out.println(user.getDevices().size());
        }
        // first solution - join fetch
        List<User> usersQuery = entityManager.createQuery(
                        "SELECT a FROM User a JOIN FETCH a.devices", User.class)
                .getResultList();
        // second solution - use BatchSize
        // third solution - use entity graph
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("User.devices");
        List<User> usersEntityGraph = entityManager.createQuery(
                        "SELECT d FROM User d", User.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
        return users;
    }
}
