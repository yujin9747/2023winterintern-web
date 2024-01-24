package com.example.kepcoweb.controller

import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.service.DatabaseService
import com.example.kepcoweb.service.KepcoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model


@Controller
@RequestMapping("/")
class DatabaseController (
    val service: DatabaseService
){
    @GetMapping("/drop-tables")
    fun getKepco(): String {
        service.dropTables()
        return "redirect:/"
    }

}