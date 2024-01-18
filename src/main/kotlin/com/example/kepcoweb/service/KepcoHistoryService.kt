package com.example.kepcoweb.service

import com.example.kepcoweb.dto.DtoUtils
import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.repository.KepcoHistoryRepository
import com.example.kepcoweb.repository.MonthSeasonHistoryRepository
import com.example.kepcoweb.repository.TimeLoadHistoryRepository
import java.time.LocalDate
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

    fun getBeforeKepcoTable(today: LocalDateTime): List<KepcoDto> {
        val current = repository.findCurrentAppliedPeriod(today) ?: return emptyList()
        val before = repository.findBeforeAppliedPeriod(current.appliedPeriod!!) ?: return emptyList()

        return repository.findAllByAppliedPeriod(before.appliedPeriod!!)
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }

    fun checkChanged(current: List<KepcoDto>, before: List<KepcoDto>): List<KepcoDto> {
        var result = current

        for (i in current.indices) {
            result[i].useValChanged = current[i].useVal != before[i].useVal
            result[i].gb1Changed = current[i].gb1 != before[i].gb1
            result[i].gb2Changed = current[i].gb2 != before[i].gb2
            result[i].selValChanged = current[i].selVal != before[i].selVal
            result[i].baseFeeChanged = current[i].baseFee != before[i].baseFee
            result[i].loadValChanged = current[i].loadVal != before[i].loadVal
            result[i].sufChanged = current[i].suf != before[i].suf
            result[i].fafChanged = current[i].faf != before[i].faf
            result[i].wifChanged = current[i].wif != before[i].wif
        }
        return result
    }

    fun getKepcoHistoryByAppliedPeriod(selectedPeriod: LocalDate): List<KepcoDto> {
        return repository.findAllByAppliedPeriod(LocalDateTime.of(selectedPeriod.year, selectedPeriod.month, selectedPeriod.dayOfMonth, 0, 0, 0))
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }

    fun getAppliedPeriodsDistinct(): List<LocalDateTime> {
        return repository.findDistinctByAppliedPeriod()
    }

    fun getFutureKepcoTable(today: LocalDateTime): List<KepcoDto> {
        val future = repository.findFutureAppliedPeriod(today) ?: return emptyList()

        return repository.findAllByAppliedPeriod(future.appliedPeriod!!)
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }
}