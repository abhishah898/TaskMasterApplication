package com.learn.TaskMaster.controller;

import com.learn.TaskMaster.entity.User;
import com.learn.TaskMaster.entity.UserDTO;
import com.learn.TaskMaster.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = authenticationService.registerUser(userDTO);
        return registeredUser;
    }
}
