package com.example.kepcoweb.service

import com.example.kepcoweb.dto.DtoUtils
import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.repository.KepcoHistoryRepository
import com.example.kepcoweb.repository.MonthSeasonHistoryRepository
import com.example.kepcoweb.repository.TimeLoadHistoryRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class KepcoHistoryService(
    val repository: KepcoHistoryRepository,
    val timeLoadRepository: TimeLoadHistoryRepository,
    val monthSeasonRepository: MonthSeasonHistoryRepository
) {
    fun getKepcoHistory(): List<KepcoDto> {
        return repository.findAll()
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }

    fun getTimeLoadHistory(): List<TimeLoadDto> {
        return timeLoadRepository.findAll()
            .map {
                DtoUtils.createTimeLoadDto(it)
            }
    }

    fun getMonthSeasonHistory(): List<MonthSeasonDto> {
        return monthSeasonRepository.findAll()
            .map {
                DtoUtils.createMonthSeasonDto(it)
            }
    }

    fun getCurrentKepcoTable(today: LocalDateTime): List<KepcoDto> {
        val current = repository.findCurrentAppliedPeriod(today) ?: return emptyList()

        return repository.findAllByAppliedPeriod(current.appliedPeriod!!)
            .map{
                DtoUtils.createKepcoDto(it)
            }
    }

    fun getFutureKepcoTable(today: LocalDateTime): List<KepcoDto> {
        return repository.findAllFutureTable(today)
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }
}