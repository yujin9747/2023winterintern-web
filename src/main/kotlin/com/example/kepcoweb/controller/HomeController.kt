package com.example.kepcoweb.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/home")
class HomeController {
    @GetMapping("/hello")
    fun hello(): String {
        return "test"
    }
}