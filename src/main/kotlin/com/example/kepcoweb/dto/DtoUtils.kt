package com.example.kepcoweb.dto

import com.example.kepcoweb.domain.Kepco
import com.example.kepcoweb.domain.KepcoHistory
import com.example.kepcoweb.domain.TimeLoad

class DtoUtils {

    companion object {
        fun createKepcoDto(it: Kepco) = KepcoDto(
            id = it.id,
            useVal = it.useVal,
            gb1 = it.gb1,
            gb2 = it.gb2,
            selVal = it.selVal,
            baseFee = it.baseFee,
            loadVal = it.loadVal,
            suf = it.suf,
            faf = it.faf,
            wif = it.wif,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            appliedPeriod = it.appliedPeriod
        )

        fun createKepcoDto(it: KepcoHistory) = KepcoDto(
            id = it.id,
            useVal = it.useVal,
            gb1 = it.gb1,
            gb2 = it.gb2,
            selVal = it.selVal,
            baseFee = it.baseFee,
            loadVal = it.loadVal,
            suf = it.suf,
            faf = it.faf,
            wif = it.wif,
            createdAt = it.createdAt,
            appliedPeriod = it.appliedPeriod
        )

        fun createTimeLoadDto(it: TimeLoad) = TimeLoadDto(
            id = it.id,
            season = it.season,
            hour = it.hour,
            loadVal = it.loadVal,
            createdAt = it.createdAt,
            appliedPeriod = it.appliedPeriod
        )
    }
}