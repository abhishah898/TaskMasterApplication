package com.learn.TaskMaster.controller;

import com.learn.TaskMaster.entity.User;
import com.learn.TaskMaster.entity.UserDTO;
import com.learn.TaskMaster.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = authenticationService.registerUser(userDTO);
        String generatedToken = UUID.randomUUID().toString();
        String verificationUrl = "http://localhost:4000/api/verifyRegistration?token=" + generatedToken;
        authenticationService.persistRegistrationToken(registeredUser, generatedToken);
        System.out.println("Verify you identity using the following URL: ");
        System.out.println(verificationUrl);
        return registeredUser;
    }

    @PostMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        boolean isTokenValid = authenticationService.verifyRegistrationToken(token);

        if (!isTokenValid) {
            return "Token Verification failed!";
        }

        authenticationService.enableUser(token);
        return "User verified successfully";
    }
}
