package com.example.kepcoweb.service

import com.example.kepcoweb.domain.KepcoHistory
import com.example.kepcoweb.repository.DatabaseRepository
import com.example.kepcoweb.repository.KepcoHistoryRepository
import java.time.LocalDate
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

    fun insertTomorrowTable() {
        // 2024-01-01 요금표를 가져와서 값을 변경 후 내일 적용되는 요금표를 삽입한다.
        val tomorrow = LocalDate.now().plusDays(1)

        var table20240101 =
            kepcoHistoryRepository.findAllByAppliedPeriod(LocalDateTime.of(2024, 1, 1, 0, 0, 0))

        var tomorrowTable = table20240101.map {
            KepcoHistory().apply {
                useVal = it.useVal
                gb1 = it.gb1
                gb2 = it.gb2
                selVal = it.selVal
                baseFee = it.baseFee
                loadVal = it.loadVal
                suf = it.suf
                faf = it.faf
                wif = it.wif
                appliedPeriod = LocalDateTime.of(tomorrow.year, tomorrow.month, tomorrow.dayOfMonth, 0, 0, 0)
            }
        }

        // 값을 일부 변경
        tomorrowTable[0].baseFee = 7000
        tomorrowTable[5].suf = 100.3f
        tomorrowTable[17].faf = 120.5f
        tomorrowTable[18].wif = 170.6f

        kepcoHistoryRepository.saveAll(tomorrowTable)
    }

    fun changeCraetedAtAndUpdatedAt(date: LocalDateTime, updatedBy: LocalDate) {
        val appliedPeriod = LocalDateTime.of(updatedBy.year, updatedBy.month, updatedBy.dayOfMonth, 0, 0, 0)
        var table =
            kepcoHistoryRepository.findAllByAppliedPeriod(appliedPeriod)

        table.map {
            it.apply {
                createdAt = date
                updatedAt = date
            }
        }
    }

    fun insertFutureTable() {
        var table20240101 =
            kepcoHistoryRepository.findAllByAppliedPeriod(LocalDateTime.of(2024, 1, 1, 0, 0, 0))

        var futureTable = table20240101.map {
            KepcoHistory().apply {
                useVal = it.useVal
                gb1 = it.gb1
                gb2 = it.gb2
                selVal = it.selVal
                baseFee = it.baseFee
                loadVal = it.loadVal
                suf = it.suf
                faf = it.faf
                wif = it.wif
                appliedPeriod = LocalDateTime.of(2024, 3, 1, 0, 0, 0)
            }
        }

        // 값을 일부 변경
        futureTable[0].baseFee = 8000
        futureTable[5].suf = 120.3f
        futureTable[17].faf = 130.5f
        futureTable[18].wif = 180.6f

        kepcoHistoryRepository.saveAll(futureTable)
    }

    fun deleteFutureTable() {
        val future = LocalDateTime.of(2024, 3, 1, 0, 0, 0)
        kepcoHistoryRepository.deleteAllByAppliedPeriod(future)
    }

    fun deleteTomorrowTable() {
        val tomorrow = LocalDate.now().plusDays(1)
        val appliedPeriod = LocalDateTime.of(tomorrow.year, tomorrow.month, tomorrow.dayOfMonth, 0, 0, 0)
        kepcoHistoryRepository.deleteAllByAppliedPeriod(appliedPeriod)
    }
}