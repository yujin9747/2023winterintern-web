package com.example.kepcoweb.dto

import java.time.LocalDateTime

class TimeLoadDto (
    val id: Int? = null,
    val season: String? = null,
    val hour: Int? = null,
    val loadVal: Int? = null,
    val createdAt: LocalDateTime? = null,
    val appliedPeriod: LocalDateTime? = null,
)