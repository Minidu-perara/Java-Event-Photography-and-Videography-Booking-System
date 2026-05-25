package com.photobooking.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// handle all exceptions globally
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, RedirectAttributes flash) {
        // log the error and redirect to error page with message
        String message = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred";
        flash.addFlashAttribute("errorMsg", message);
        return "redirect:/error";
    }
}
