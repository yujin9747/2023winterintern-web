package com.example.kepcoweb.service

import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.repository.KepcoHistoryRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class KepcoHistoryService(
    val repository: KepcoHistoryRepository
) {
    fun getKepcoHistory(): List<KepcoDto> {
        return repository.findAll()
            .map {
                KepcoDto(
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
            }
    }

    fun getCurrentKepcoTable(today: LocalDateTime): List<KepcoDto> {
        return repository.findAllCurrentTable(today)
            .map{
                KepcoDto(
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
            }
    }

    fun getFutureKepcoTable(today: LocalDateTime): List<KepcoDto> {
        return repository.findAllFutureTable(today)
            .map{
                KepcoDto(
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
            }
    }
}