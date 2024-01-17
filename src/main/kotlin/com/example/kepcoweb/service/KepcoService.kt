package com.example.kepcoweb.service

import com.example.kepcoweb.dto.DtoUtils
import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.dto.MonthSeasonDto
import com.example.kepcoweb.dto.TimeLoadDto
import com.example.kepcoweb.repository.KepcoRepository
import com.example.kepcoweb.repository.MonthSeasonRepository
import com.example.kepcoweb.repository.TimeLoadRepository
import org.springframework.stereotype.Service

@Service
class KepcoService(
    val repository: KepcoRepository,
    val timeLaodRepository: TimeLoadRepository,
    val monthSeasonRepository: MonthSeasonRepository
) {

    fun getKepco(): List<KepcoDto> {
        return repository.findAll()
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }

    fun getTimeLoad(): List<TimeLoadDto> {
        return timeLaodRepository.findAll()
            .map {
                DtoUtils.createTimeLoadDto(it)
            }
    }

    fun getMonthSeason(): List<MonthSeasonDto> {
        return monthSeasonRepository.findAll()
            .map {
                DtoUtils.createMonthSeasonDto(it)
            }
    }
}