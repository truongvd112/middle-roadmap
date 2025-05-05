package com.example.middle_roadmap.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RedisMessageSubscriber implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Received message: " + new String(message.getBody(), StandardCharsets.UTF_8));
        System.out.println("Received message from topic: " + new String(message.getChannel(), StandardCharsets.UTF_8));
    }
}
