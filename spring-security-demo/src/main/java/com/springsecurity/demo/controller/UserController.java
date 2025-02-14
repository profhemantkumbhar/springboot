package com.springsecurity.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/user")
    public String user() {
        return "Hi User";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hi Admin";
    }
}
