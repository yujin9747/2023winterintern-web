package com.example.kepcoweb.controller

import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.service.CronService
import com.example.kepcoweb.service.DatabaseService
import com.example.kepcoweb.service.KepcoService
import jakarta.servlet.http.HttpServletRequest
import java.time.LocalDate
import java.time.LocalDateTime
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model


@Controller
@RequestMapping("/")
class DatabaseController (
    val service: DatabaseService,
    val cronService: CronService
){
    @GetMapping("/drop-tables")
    fun getKepco(request: HttpServletRequest): String {
        service.dropTables()

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram"
    }

    @GetMapping("/update-current-table")
    fun updateCurrentTable(request: HttpServletRequest): String {
        service.deleteFutureTable()
        service.deleteTomorrowTable()
        service.updateCurrentTable()
        cronService.executeCron2()

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram"
    }

    @GetMapping("/insert-tomorrow-table")
    fun insertTomorrowTable(request: HttpServletRequest): String {
        service.insertTomorrowTable()

        val tomorrow = LocalDate.now().plusDays(1)
        service.changeCraetedAtAndUpdatedAt(LocalDateTime.of(2024, 1, 10, 12, 30, 34), tomorrow)

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram"
    }

    @GetMapping("/insert-future-table")
    fun insertFutureTable(request: HttpServletRequest): String {
        service.insertFutureTable()

        val future = LocalDate.of(2024, 3, 1)
        service.changeCraetedAtAndUpdatedAt(LocalDateTime.of(2024, 1, 20, 12, 30, 34), future)

        val referer = request.getHeader("Referer")
        val beforeQueryPeram = referer.substringBefore("?")
        return "redirect:$beforeQueryPeram"
    }

}