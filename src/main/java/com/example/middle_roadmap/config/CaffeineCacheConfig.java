package com.example.middle_roadmap.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        CompositeCacheManager cacheManager = new CompositeCacheManager();

        CaffeineCacheManager caffeineCacheManager  = new CaffeineCacheManager("devices", "users");
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(200)  // Giới hạn tối đa 200 items trong cache
                .expireAfterWrite(10, TimeUnit.MINUTES)  // Hết hạn sau 10 phút
                .recordStats()); // Bật thống kê cache
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory).build();

        cacheManager.setCacheManagers(Arrays.asList(caffeineCacheManager, redisCacheManager));
        return cacheManager;
    }
}
