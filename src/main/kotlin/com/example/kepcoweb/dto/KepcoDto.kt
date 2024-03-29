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
    val current: LocalDateTime? = null,


    var useValChanged: Boolean? = false,
    var gb1Changed: Boolean? = false,
    var gb2Changed: Boolean? = false,
    var selValChanged: Boolean? = false,
    var baseFeeChanged: Boolean? = false,
    var loadValChanged: Boolean? = false,
    var sufChanged: Boolean? = false,
    var fafChanged: Boolean? = false,
    var wifChanged: Boolean? = false,
    var periodChanged: Boolean? = false
)