package com.example.middle_roadmap.controller;

import com.example.middle_roadmap.config.RedisMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/user")
public class MessageController {
    @Autowired
    private RedisMessagePublisher publisher;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        publisher.publish("news", message);
        return "Message sent!";
    }
}
