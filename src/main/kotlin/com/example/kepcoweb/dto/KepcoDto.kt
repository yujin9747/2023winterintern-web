package com.example.kepcoweb.dto

import java.time.LocalDateTime

class KepcoDto (
    val id: Int? = null,
    val useVal: Int? = null,
    val gb1: Int? = null,
    val gb2: String? = null,
    val selVal: Int? = null,
    val baseFee: Int? = null,
    val loadVal: Int? = null,
    val suf: Float? = null,
    val faf: Float? = null,
    val wif: Float? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val appliedPeriod: LocalDateTime? = null,
    val current: LocalDateTime? = null
)