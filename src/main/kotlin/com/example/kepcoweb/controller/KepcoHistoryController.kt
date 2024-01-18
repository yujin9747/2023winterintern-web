package com.example.kepcoweb.controller

import com.example.kepcoweb.service.KepcoHistoryService
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/timeLoad")
    fun getTimeLoadHistory(model: Model): String {
        model.addAttribute("message", "electric_rates_timeLoad_history 테이블 정보 조회 페이지")

        val timeLoadHistory = service.getTimeLoadHistory()
        model.addAttribute("timeLoad", timeLoadHistory)
        return "kepco_timeLoad"
    }

    @GetMapping("/monthSeason")
    fun getMonthSeasonHistory(model: Model): String {
        model.addAttribute("message", "electric_rates_month_season_history 테이블 정보 조회 페이지")

        val monthSeason = service.getMonthSeasonHistory()
        model.addAttribute("monthSeason", monthSeason)
        return "kepco_monthSeason"
    }

    // today는 여러 상황을 보여주기 위해 임의로 넣은 것
    @GetMapping("/current")
    fun getCurrentKepcoTable(
        model: Model,
        @RequestParam("year") year: Int?,
        @RequestParam("month") month: Int?,
        @RequestParam("day") day: Int?
    ): String {
        model.addAttribute("message", "현재 날짜 기준 적용되는 요금표 조회")

        // 기준이 될 오늘 날짜 넘기기
        var today = LocalDateTime.now()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }

        val kepcoCurrent = service.getCurrentKepcoTable(today)

        if (kepcoCurrent.isEmpty()) {
            model.addAttribute("message", "현재 날짜보다 이전에 적용된 요금표가 없습니다.")
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        // 해당 요금표가 적용된지 며칠이 지났는지 D+ 계산하기
        val days = today.differentDays(kepcoCurrent[0].appliedPeriod!!)

        model.addAttribute("kepcoCurrent", kepcoCurrent)
        model.addAttribute("today", today.toLocalDate())
        model.addAttribute("days", days)
        return "kepco_current"
    }

    @GetMapping("/future")
    fun getFutureKepcoTable(
        model: Model,
        @RequestParam("year") year: Int?,
        @RequestParam("month") month: Int?,
        @RequestParam("day") day: Int?
    ): String {
        model.addAttribute("message", "현재 날짜 이후에 적용될 요금표 조회")

        // 기준이 될 오늘 날짜 넘기기
        var today = LocalDateTime.now()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }
        val kepcoFuture = service.getFutureKepcoTable(today)

        // 해당 요금표가 적용된지 며칠이 지났는지 D- 계산하기
        if (kepcoFuture.isEmpty()) {
            model.addAttribute("message", "현재 날짜보다 미래에 적용될 요금표가 없습니다.")
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }
        val days = today.differentDays(kepcoFuture[0].appliedPeriod!!)

        model.addAttribute("kepcoFuture", kepcoFuture)
        model.addAttribute("today", today.toLocalDate())
        model.addAttribute("days", days)
        return "kepco_future"
    }

    @GetMapping("/current-before")
    fun getCurrentAndBeforeKepcoTable(
        model: Model,
        @RequestParam("year") year: Int?,
        @RequestParam("month") month: Int?,
        @RequestParam("day") day: Int?
    ) : String {
        model.addAttribute("message", "현재 적용중인 요금표와 이전에 적용된 요금표 비교 조회")

        // 기준이 될 오늘 날짜 넘기기
        var today = LocalDateTime.now()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }
        var kepcoCurrent = service.getCurrentKepcoTable(today)
        val kepcoBefore = service.getBeforeKepcoTable(today)

        // 해당 요금표가 적용된지 며칠이 지났는지 D+ 계산하기
        if (kepcoCurrent.isEmpty()) {
            model.addAttribute("message", "현재 날짜보다 이전에 적용된 요금표가 없습니다.")
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }
        val days = today.differentDays(kepcoCurrent[0].appliedPeriod!!)

        if (kepcoBefore.isEmpty()) {
            model.addAttribute("message", "현재 적용중인 요금표와 비교할 이전에 적용된 요금표가 없습니다.")
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        kepcoCurrent = service.checkChanged(kepcoCurrent, kepcoBefore)

        model.addAttribute("kepcoCurrent", kepcoCurrent)
        model.addAttribute("kepcoBefore", kepcoBefore)
        model.addAttribute("today", today.toLocalDate())
        model.addAttribute("days", days)
        return "kepco_current_before"
    }

    @GetMapping("/current-future")
    fun getCurrentAndFutureKepcoTable(
        model: Model,
        @RequestParam("year") year: Int?,
        @RequestParam("month") month: Int?,
        @RequestParam("day") day: Int?
    ) : String {
        model.addAttribute("message", "현재 적용중인 요금표와 적용 예정인 요금표 비교 조회")

        // 기준이 될 오늘 날짜 넘기기
        var today = LocalDateTime.now()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }
        var kepcoCurrent = service.getCurrentKepcoTable(today)
        val kepcoFuture = service.getFutureKepcoTable(today)

        // 해당 요금표가 적용된지 며칠이 지났는지 D+ 계산하기
        if (kepcoCurrent.isEmpty()) {
            model.addAttribute("message", "현재 날짜보다 미래에 적용 예정인 요금표가 없습니다.")
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }
        val days = today.differentDays(kepcoCurrent[0].appliedPeriod!!)

        if (kepcoFuture.isEmpty()) {
            model.addAttribute("message", "현재 적용중인 요금표와 비교할 적용 예정인 요금표가 없습니다.")
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        kepcoCurrent = service.checkChanged(kepcoCurrent, kepcoFuture)

        model.addAttribute("kepcoCurrent", kepcoCurrent)
        model.addAttribute("kepcoFuture", kepcoFuture)
        model.addAttribute("today", today.toLocalDate())
        model.addAttribute("days", days)
        return "kepco_current_future"
    }


    internal fun LocalDateTime.differentDays(from: LocalDateTime): Long {
        return abs(ChronoUnit.DAYS.between(this, from))
    }
}