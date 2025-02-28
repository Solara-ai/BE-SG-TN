package org.se06203.besgtn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
