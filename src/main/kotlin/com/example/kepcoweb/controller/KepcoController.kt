package com.example.kepcoweb.controller

import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.service.KepcoService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model


@Controller
@RequestMapping("/kepco")
class KepcoController (
    val service: KepcoService
){
    @GetMapping
    fun getKepco(model: Model): String {
        model.addAttribute("message", "electric_rates 테이블 정보 조회 페이지")

        var kepco: List<KepcoDto>
        try {
            kepco = getKepco()
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }

        model.addAttribute("kepco", kepco)
        return "kepco"
    }

    @GetMapping("/timeLoad")
    fun getTimeLoad(model: Model): String {
        model.addAttribute("message", "electric_rates_timeLoad 테이블 정보 조회 페이지")

        var kepcoTimeLoad: List<TimeLoadDto>
        try {
            kepcoTimeLoad = getTimeLoad()
        } catch (e: Exception) {
            model.addAttribute("message", e.message)
            return "kepco_error"
        }

        model.addAttribute("timeLoad", kepcoTimeLoad)
        return "kepco_timeLoad"
    }

    @GetMapping("/monthSeason")
    fun getMonthSeason(model: Model): String {
        model.addAttribute("message", "electric_rates_month_season 테이블 정보 조회 페이지")

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

    internal fun getKepco(): List<KepcoDto> {
        var kepco: List<KepcoDto>
        try {
            kepco = service.getKepco()
            if (kepco.isEmpty()) {
                throw Exception("electric_rates 테이블이 존재하나, 데이터가 없습니다")
            }
        } catch (e: Exception) {
            throw Exception("electric_rates 테이블이 존재하지 않습니다")
        }
        return kepco
    }

    internal fun getTimeLoad(): List<TimeLoadDto> {
        var timeLoad: List<TimeLoadDto>
        try {
            timeLoad = service.getTimeLoad()
            if (timeLoad.isEmpty()) {
                throw Exception("electric_rates_timeLoad 테이블이 존재하나, 데이터가 없습니다")
            }
        } catch (e: Exception) {
            throw Exception("electric_rates_timeLoad 테이블이 존재하지 않습니다")
        }
        return timeLoad
    }

    internal fun getMonthSeason(): List<MonthSeasonDto> {
        var monthSeason: List<MonthSeasonDto>
        try {
            monthSeason = service.getMonthSeason()
            if (monthSeason.isEmpty()) {
                throw Exception("electric_rates_month_season 테이블이 존재하나, 데이터가 없습니다")
            }
        } catch (e: Exception) {
            throw Exception("electric_rates_month_season 테이블이 존재하지 않습니다")
        }
        return monthSeason
    }


}