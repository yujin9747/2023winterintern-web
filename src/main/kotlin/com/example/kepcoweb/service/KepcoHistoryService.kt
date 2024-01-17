package com.example.kepcoweb.service

import com.example.kepcoweb.dto.DtoUtils
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
                DtoUtils.createKepcoDto(it)
            }
    }

    fun getCurrentKepcoTable(today: LocalDateTime): List<KepcoDto> {
        return repository.findAllCurrentTable(today)
            .map{
                DtoUtils.createKepcoDto(it)
            }
    }

    fun getFutureKepcoTable(today: LocalDateTime): List<KepcoDto> {
        return repository.findAllFutureTable(today)
            .map{
                DtoUtils.createKepcoDto(it)
            }
    }
}