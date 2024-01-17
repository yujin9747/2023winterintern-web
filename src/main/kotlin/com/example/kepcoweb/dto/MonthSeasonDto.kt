package com.example.kepcoweb.dto

import java.time.LocalDateTime

class MonthSeasonDto (
    val id: Int? = null,
    val month: Int? = null,
    val season: String? = null,
    val createdAt: LocalDateTime? = null,
    val appliedPeriod: LocalDateTime? = null,
)