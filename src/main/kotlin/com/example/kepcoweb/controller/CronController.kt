package com.example.kepcoweb.controller

import com.example.kepcoweb.service.CronService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

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
    fun executeCron2(
        request: HttpServletRequest,
        @RequestParam("success", required = false) success: Boolean?,
        @RequestParam("reason", required = false) reason: String?
    ): String {

        var success = true
        var reason = ""
        try {
            service.executeCron2()
        } catch (e: Exception) {
            success = false
            reason = e.message!!
        }

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram?success=$success&reason=$reason"
    }
}