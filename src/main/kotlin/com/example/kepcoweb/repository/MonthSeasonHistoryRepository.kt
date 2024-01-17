package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.MonthSeasonHistory
import org.springframework.data.jpa.repository.JpaRepository

interface MonthSeasonHistoryRepository : JpaRepository<MonthSeasonHistory, Int> {
}