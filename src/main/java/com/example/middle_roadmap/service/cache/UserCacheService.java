package com.example.middle_roadmap.service.cache;

import com.example.middle_roadmap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCacheService {
    private final UserRepository userRepository;
    @Autowired
    private CacheManager cacheManager;

    @CacheEvict(value = "users", key = "#id")
    public void deleteById(Long id){
        userRepository.deleteById(String.valueOf(id));
    }
}
