package com.example.middle_roadmap.controller;

import com.example.middle_roadmap.entity.User;
import com.example.middle_roadmap.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> bankTransfer(@RequestParam long userId1, @RequestParam long userId2) {
        return new ResponseEntity<>(userService.bankTransfer(userId1, userId2), HttpStatus.OK);
    }

    @PostMapping("/3-thread")
    public ResponseEntity<Object> testThread() {
        return new ResponseEntity<>(userService.threadExample(), HttpStatus.OK);
    }
}
