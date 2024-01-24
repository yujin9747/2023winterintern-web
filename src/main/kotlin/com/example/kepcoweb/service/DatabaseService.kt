package com.example.kepcoweb.service

import com.example.kepcoweb.domain.KepcoHistory
import com.example.kepcoweb.repository.DatabaseRepository
import com.example.kepcoweb.repository.KepcoHistoryRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DatabaseService(
    val repository: DatabaseRepository,
    val kepcoHistoryRepository: KepcoHistoryRepository
) {
    fun dropTables() {
        repository.dropTables()
    }

    fun updateCurrentTable() {
        // 오늘 : 2024-02-01
        // 현재 적용중인 요금표 : 2024-01-01
        var table20240101 =
            kepcoHistoryRepository.findAllByAppliedPeriod(LocalDateTime.of(2024, 1, 1, 0, 0, 0))

        var updatedTable20240101 = table20240101.map {
            KepcoHistory().apply {
                id = it.id
                useVal = it.useVal
                gb1 = it.gb1
                gb2 = it.gb2
                selVal = it.selVal
                baseFee = it.baseFee
                loadVal = it.loadVal
                suf = it.suf
                faf = it.faf
                wif = it.wif
                appliedPeriod = it.appliedPeriod
                createdAt = it.createdAt
            }
        }

        updatedTable20240101[0].baseFee = 6000
        updatedTable20240101[5].suf = 97.2f
        updatedTable20240101[17].faf = 110.5f
        updatedTable20240101[18].wif = 160.6f


        // 변경된 값으로 update
        kepcoHistoryRepository.saveAll(updatedTable20240101)
    }
}