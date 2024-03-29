package com.example.kepcoweb.controller

import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.service.KepcoHistoryService
import com.example.kepcoweb.service.KepcoService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
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
    val service: KepcoHistoryService,
    val kepcoService: KepcoService
){
    @GetMapping
    fun getKepcoHistory(
        model: Model,
        selectedPeriod: LocalDate?
    ): String {
        model.addAttribute("message", "electric_rates_history 테이블 정보 조회 페이지")

        val kepcoHistory: List<KepcoDto>
        try {
            kepcoHistory = getFilteredKepcoHistory(selectedPeriod)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }

        val appliedPeriodSet = service.getAppliedPeriodsDistinct().map { it.toLocalDate() }

        model.addAttribute("kepcoHistory", kepcoHistory)
        model.addAttribute("appliedPeriods", appliedPeriodSet)
        model.addAttribute("selectedPeriod", selectedPeriod)
        return "kepco_history"
    }

    @GetMapping("/timeLoad")
    fun getTimeLoadHistory(model: Model): String {
        model.addAttribute("message", "electric_rates_timeLoad_history 테이블 정보 조회 페이지")

        var timeLoadHistory: List<TimeLoadDto>
        try {
            timeLoadHistory = getTimeLoad()
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }
        model.addAttribute("timeLoad", timeLoadHistory)
        return "kepco_timeLoad"
    }

    @GetMapping("/monthSeason")
    fun getMonthSeasonHistory(model: Model): String {
        model.addAttribute("message", "electric_rates_month_season_history 테이블 정보 조회 페이지")

        var monthSeason: List<MonthSeasonDto>
        try {
            monthSeason = getMonthSeason()
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }
        model.addAttribute("monthSeason", monthSeason)
        return "kepco_monthSeason"
    }

    @GetMapping("/current")
    fun getCurrentKepcoTable(
        model: Model,
        @RequestParam("year") year: Int?,
        @RequestParam("month") month: Int?,
        @RequestParam("day") day: Int?
    ): String {
        model.addAttribute("message", "현재 날짜 기준 적용되는 요금표 조회")

        var today = getToday()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }

        val kepcoCurrent: List<KepcoDto>
        try {
            kepcoCurrent = getCurrentKepcoTable(today)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
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
        var today = getToday()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }

        var kepcoFuture: List<KepcoDto>
        try {
            kepcoFuture = getFutureKepcoTable(today)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
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
        var today = getToday()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }

        var kepcoCurrent: List<KepcoDto>
        try {
            kepcoCurrent = getCurrentKepcoTable(today)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        var kepcoBefore: List<KepcoDto>
        try {
            kepcoBefore = getBeforeKepcoTable(today)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        val days = today.differentDays(kepcoCurrent[0].appliedPeriod!!)

        kepcoCurrent = service.checkDifference(kepcoCurrent, kepcoBefore)
        // kepcoBefore의 changed 값을 kepcoCurrent 값으로 맞추기
        for (i in kepcoBefore.indices) {
            kepcoBefore[i].useValChanged = kepcoCurrent[i].useValChanged
            kepcoBefore[i].gb1Changed = kepcoCurrent[i].gb1Changed
            kepcoBefore[i].gb2Changed = kepcoCurrent[i].gb2Changed
            kepcoBefore[i].selValChanged = kepcoCurrent[i].selValChanged
            kepcoBefore[i].baseFeeChanged = kepcoCurrent[i].baseFeeChanged
            kepcoBefore[i].loadValChanged = kepcoCurrent[i].loadValChanged
            kepcoBefore[i].sufChanged = kepcoCurrent[i].sufChanged
            kepcoBefore[i].fafChanged = kepcoCurrent[i].fafChanged
            kepcoBefore[i].wifChanged = kepcoCurrent[i].wifChanged
        }


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
        var today = getToday()

        if (year != null && month != null && day != null){
            today = LocalDateTime.of(year, month, day, 0, 0, 0)
        }

        var kepcoCurrent: List<KepcoDto>
        try {
            kepcoCurrent = getCurrentKepcoTable(today)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        val days = today.differentDays(kepcoCurrent[0].appliedPeriod!!)

        var kepcoFuture: List<KepcoDto>
        try {
            kepcoFuture = getFutureKepcoTable(today)
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            model.addAttribute("today", today.toLocalDate())
            return "kepco_error"
        }

        kepcoCurrent = service.checkDifference(kepcoCurrent, kepcoFuture)
        // kepcoFuture의 changed 값을 kepcoCurrent 값으로 맞추기
        for(i in kepcoFuture.indices) {
            kepcoFuture[i].useValChanged = kepcoCurrent[i].useValChanged
            kepcoFuture[i].gb1Changed = kepcoCurrent[i].gb1Changed
            kepcoFuture[i].gb2Changed = kepcoCurrent[i].gb2Changed
            kepcoFuture[i].selValChanged = kepcoCurrent[i].selValChanged
            kepcoFuture[i].baseFeeChanged = kepcoCurrent[i].baseFeeChanged
            kepcoFuture[i].loadValChanged = kepcoCurrent[i].loadValChanged
            kepcoFuture[i].sufChanged = kepcoCurrent[i].sufChanged
            kepcoFuture[i].fafChanged = kepcoCurrent[i].fafChanged
            kepcoFuture[i].wifChanged = kepcoCurrent[i].wifChanged
        }

        model.addAttribute("kepcoCurrent", kepcoCurrent)
        model.addAttribute("kepcoFuture", kepcoFuture)
        model.addAttribute("today", today.toLocalDate())
        model.addAttribute("days", days)
        return "kepco_current_future"
    }

    @GetMapping("/compare-current")
    fun getCurrentCompareResult(
        model: Model,
        selectedPeriod: LocalDate?
    ): String {
        model.addAttribute("message", "electric_rates와 electric_rates_history 테이블 비교 조회")

        var isNotExistRates = false
        var isNotExistHistory = false
        var filteredBy: LocalDate? = null
        if (selectedPeriod == null) {
            try {
                filteredBy = service.getCurrentAppliedPeriodByTomorrow()?.toLocalDate()
            } catch (e: Exception) {
                isNotExistHistory = true
            }
        }
        else {
            filteredBy = selectedPeriod
        }

        var kepco: List<KepcoDto> = emptyList()
        try {
            try{
                kepco = getKepco()
            } catch (e: Exception) {
                isNotExistRates = true
            }
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }

        var kepcoHistory: List<KepcoDto> = emptyList()
        try {
            try {
                kepcoHistory = getFilteredKepcoHistory(filteredBy)
            } catch (e: Exception) {
                isNotExistHistory = true
            }
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }

        var appliedPeriodSet: List<LocalDate> = emptyList()
        if (!isNotExistHistory) appliedPeriodSet = service.getAppliedPeriodsDistinct().map { it.toLocalDate() }

        if (filteredBy != null) {
            kepco = service.checkDifference(kepco, kepcoHistory)
            kepco = service.checkAppliedPeriodDifference(kepco, kepcoHistory)
        }

        if (kepcoHistory.size == kepco.size) {
            for (i in kepcoHistory.indices) {
                kepcoHistory[i].useValChanged = kepco[i].useValChanged
                kepcoHistory[i].gb1Changed = kepco[i].gb1Changed
                kepcoHistory[i].gb2Changed = kepco[i].gb2Changed
                kepcoHistory[i].selValChanged = kepco[i].selValChanged
                kepcoHistory[i].baseFeeChanged = kepco[i].baseFeeChanged
                kepcoHistory[i].loadValChanged = kepco[i].loadValChanged
                kepcoHistory[i].sufChanged = kepco[i].sufChanged
                kepcoHistory[i].fafChanged = kepco[i].fafChanged
                kepcoHistory[i].wifChanged = kepco[i].wifChanged
                kepcoHistory[i].periodChanged = kepco[i].periodChanged
            }
        }

        if (isNotExistRates) {
            model.addAttribute("existRates", "electric_rates 테이블이 존재하지 않습니다.")
        }
        if (isNotExistHistory) {
            model.addAttribute("existHistory", "electric_rates_history 테이블이 존재하지 않습니다.")
        }

        model.addAttribute("kepco", kepco)
        model.addAttribute("kepcoHistory", kepcoHistory)
        if (!isNotExistHistory) model.addAttribute("appliedPeriods", appliedPeriodSet)
        model.addAttribute("selectedPeriod", filteredBy)

        return "kepco_compare"
    }


    internal fun LocalDateTime.differentDays(from: LocalDateTime): Long {
        return abs(ChronoUnit.DAYS.between(this, from))
    }

    internal fun getToday(): LocalDateTime {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()
    }

    internal fun getFilteredKepcoHistory(selectedPeriod: LocalDate?): List<KepcoDto> {
        var kepcoHistory: List<KepcoDto> = try {
            if (selectedPeriod == null) {
                service.getKepcoHistory()
            } else {
                service.getKepcoHistoryByAppliedPeriod(selectedPeriod)
            }
        } catch (e: Exception) {
            throw Exception("electric_rates_history 테이블이 존재하지 않습니다.")
        }

        if (kepcoHistory.isEmpty()) {
            throw Exception("electric_rates_history 테이블이 존재하나, 데이터가 존재하지 않습니다.")
        }
        return kepcoHistory
    }

    internal fun getKepco(): List<KepcoDto> {
        var kepco: List<KepcoDto>
        try {
            kepco = kepcoService.getKepco()
        } catch (e: Exception) {
            throw Exception("electric_rates 테이블이 존재하지 않습니다.")
        }
        if (kepco.isEmpty()) {
            throw Exception("electric_rates 테이블이 존재하나, 데이터가 존재하지 않습니다.")
        }
        return kepco
    }

    internal fun getCurrentKepcoTable(today: LocalDateTime): List<KepcoDto> {
        var kepcoCurrent: List<KepcoDto>

        try {
            kepcoCurrent = service.getCurrentKepcoTable(today)
        } catch (e: Exception) {
            throw Exception("electric_rates_history 테이블이 존재하지 않습니다.")
        }

        if (kepcoCurrent.isEmpty()) {
            throw Exception("현재 날짜보다 이전에 적용된 요금표가 없습니다.")
        }
        return kepcoCurrent
    }

    internal fun getFutureKepcoTable(today: LocalDateTime): List<KepcoDto> {
        var kepcoFuture: List<KepcoDto>
        try {
            kepcoFuture = service.getFutureKepcoTable(today)
        } catch (e: Exception) {
            throw Exception("electric_rates_history 테이블이 존재하지 않습니다.")
        }

        if (kepcoFuture.isEmpty()) {
            throw Exception("현재 날짜보다 미래에 적용될 요금표가 없습니다.")
        }
        return kepcoFuture
    }

    internal fun getBeforeKepcoTable(today: LocalDateTime): List<KepcoDto> {
        var kepcoBefore: List<KepcoDto>

        try {
            kepcoBefore = service.getBeforeKepcoTable(today)
        } catch (e: Exception) {
            throw Exception("electric_rates_history 테이블이 존재하지 않습니다.")
        }

        if (kepcoBefore.isEmpty()) {
            throw Exception("현재 날짜보다 이전에 적용된 요금표가 없습니다.")
        }
        return kepcoBefore
    }

    internal fun getTimeLoad(): List<TimeLoadDto> {
        var timeLoad: List<TimeLoadDto>
        try {
            timeLoad = service.getTimeLoadHistory()
        } catch (e: Exception) {
            throw Exception("electric_rates_timeLoad_history 테이블이 존재하지 않습니다.")
        }
        if (timeLoad.isEmpty()) {
            throw Exception("electric_rates_timeLoad_history 테이블이 존재하나, 데이터가 존재하지 않습니다.")
        }
        return timeLoad
    }

    internal fun getMonthSeason(): List<MonthSeasonDto> {
        var monthSeason: List<MonthSeasonDto>

        try {
            monthSeason = service.getMonthSeasonHistory()
        } catch (e: Exception) {
            throw Exception("electric_rates_month_season_history 테이블이 존재하지 않습니다.")
        }

        if (monthSeason.isEmpty()) {
            throw Exception("electric_rates_month_season_history 테이블이 존재하나, 데이터가 존재하지 않습니다.")
        }

        return monthSeason
    }
}