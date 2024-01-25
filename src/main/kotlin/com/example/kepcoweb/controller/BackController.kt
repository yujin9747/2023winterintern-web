package com.example.kepcoweb.controller

import com.example.kepcoweb.service.CronService
import com.example.kepcoweb.service.DatabaseService
import jakarta.servlet.http.HttpServletRequest
import java.lang.Thread.sleep
import kotlinx.coroutines.delay
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class BackController (
    val cronService: CronService,
    val dbService: DatabaseService
){
    @GetMapping("/back1")
    fun back1(request: HttpServletRequest): String {
        dbService.initializeHistoryTable()
        cronService.executeCron2()

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram"
    }

    @GetMapping("/back2")
    fun back2(request: HttpServletRequest): String {
        dbService.initializeHistoryTable()
        dbService.insertTomorrowTable()
        cronService.executeCron2()

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram"
    }
}