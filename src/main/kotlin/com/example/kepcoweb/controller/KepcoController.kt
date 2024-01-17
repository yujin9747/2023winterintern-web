package com.example.kepcoweb.controller

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

        val kepco = service.getKepco()

        model.addAttribute("kepco", kepco)
        return "kepco"
    }

    @GetMapping("/timeLoad")
    fun getTimeLoad(model: Model): String {
        model.addAttribute("message", "electric_rates_timeLoad 테이블 정보 조회 페이지")

        val kepcoTimeLoad = service.getTimeLoad()

        model.addAttribute("kepco", kepcoTimeLoad)
        return "kepco_timeLoad"
    }
}