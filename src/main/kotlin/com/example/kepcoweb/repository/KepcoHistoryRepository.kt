package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.KepcoHistory
import java.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface KepcoHistoryRepository : JpaRepository<KepcoHistory, Int> {

    @Query("SELECT k FROM KepcoHistory k " +
            "WHERE k.appliedPeriod <= :currentDate " +
            "ORDER BY k.appliedPeriod DESC " +
            "LIMIT 1")
    fun findCurrentAppliedPeriod(currentDate: LocalDateTime): KepcoHistory?

    @Query("SELECT k FROM KepcoHistory k " +
            "WHERE k.appliedPeriod <= :tomorrow " +
            "ORDER BY k.appliedPeriod DESC " +
            "LIMIT 1")
    fun findCurrentAppliedPeriodByTomorrow(tomorrow: LocalDateTime): KepcoHistory?

    @Query("SELECT k FROM KepcoHistory k " +
            "WHERE k.appliedPeriod < :currentDate " +
            "ORDER BY k.appliedPeriod DESC " +
            "LIMIT 1")
    fun findBeforeAppliedPeriod(currentDate: LocalDateTime): KepcoHistory?

    @Query("SELECT k FROM KepcoHistory k " +
            "WHERE k.appliedPeriod > :currentDate " +
            "ORDER BY k.appliedPeriod ASC " +
            "LIMIT 1")
    fun findFutureAppliedPeriod(currentDate: LocalDateTime): KepcoHistory?

    fun findAllByAppliedPeriod(currentDate: LocalDateTime): List<KepcoHistory>

    @Query("SELECT DISTINCT k.appliedPeriod FROM KepcoHistory k")
    fun findDistinctByAppliedPeriod(): List<LocalDateTime>

    fun deleteAllByAppliedPeriod(appliedPeriod: LocalDateTime)
}