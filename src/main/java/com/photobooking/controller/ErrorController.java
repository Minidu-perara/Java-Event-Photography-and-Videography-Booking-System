package com.photobooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// show error page
@Controller
public class ErrorController {

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
