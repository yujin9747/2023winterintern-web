package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.KepcoHistory
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface KepcoHistoryRepository : JpaRepository<KepcoHistory, Int> {

    @Query("SELECT k FROM KepcoHistory k " +
            "WHERE k.appliedPeriod < :currentDate " +
            "ORDER BY k.id DESC " +
            "LIMIT 44")
    fun findAllCurrentTable(currentDate: LocalDateTime): List<KepcoHistory>
}