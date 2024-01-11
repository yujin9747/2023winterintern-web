package com.example.kepcoweb.controller

import com.example.kepcoweb.service.KepcoHistoryService
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.math.abs

@Controller
@RequestMapping("/kepco-history")
class KepcoHistoryController (
    val service: KepcoHistoryService
){
    @GetMapping
    fun getKepcoHistory(model: Model): String {
        model.addAttribute("message", "electric_rates_history 테이블 정보 조회 페이지")

        val kepcoHistory = service.getKepcoHistory()
        model.addAttribute("kepcoHistory", kepcoHistory)
        return "kepco_history"
    }

    @GetMapping("/current")
    fun getCurrentKepcoTable(model: Model): String {
        model.addAttribute("message", "현재 날짜 기준 적용되는 요금표 조회")

        // 기준이 될 오늘 날짜 넘기기
        var today = LocalDateTime.now()
        today = LocalDateTime.of(2023, 12, 29, 0, 0, 0)
        val kepcoCurrent = service.getCurrentKepcoTable(today)

        // 해당 요금표가 적용된지 며칠이 지났는지 D+ 계산하기
        val days = today.differentDays(kepcoCurrent[0].appliedPeriod!!)

        model.addAttribute("kepcoCurrent", kepcoCurrent)
        model.addAttribute("today", today.toLocalDate())
        model.addAttribute("days", days)
        return "kepco_current"
    }

    internal fun LocalDateTime.differentDays(from: LocalDateTime): Long {
        return abs(ChronoUnit.DAYS.between(this, from))
    }
}