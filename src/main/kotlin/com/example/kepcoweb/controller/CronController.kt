package com.example.kepcoweb.controller

import com.example.kepcoweb.service.CronService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class CronController (
    val service: CronService
){
    @GetMapping("/execute-cron1")
    fun executeCron1(): String {
        service.executeCron1()
        return "redirect:/"
    }

    @GetMapping("/execute-cron2")
    fun executeCron2(request: HttpServletRequest): String {
        service.executeCron2()

        val referer = request.getHeader("Referer");
        return "redirect:$referer"
    }
}