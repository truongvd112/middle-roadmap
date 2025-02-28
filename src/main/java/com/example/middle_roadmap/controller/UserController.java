package com.example.middle_roadmap.controller;

import com.example.middle_roadmap.entity.Device;
import com.example.middle_roadmap.entity.User;
import com.example.middle_roadmap.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/user")
//@PreAuthorize("@userAuthorizeService.hasRole()")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> list() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/add-device")
    public ResponseEntity<Object> addDevice(
            @RequestParam(required = false) Long userId,
            @RequestBody @Valid List<Device> devices
    ) {
        return new ResponseEntity<>(userService.addDevice(devices, userId), HttpStatus.OK);
    }

    @PutMapping("/remove-device")
    public ResponseEntity<Object> removeDevice(
            @RequestBody @Valid List<Long> deviceIds
    ) {
        return new ResponseEntity<>(userService.removeDevice(deviceIds), HttpStatus.OK);
    }
}
