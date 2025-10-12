package com.learn.TaskMaster.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @GetMapping("/hello")
    public String healthCheck() {
        return "Welcome to Task Master Application!";
    }
}
