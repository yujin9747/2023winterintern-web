package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.TimeLoadHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TimeLoadHistoryRepository : JpaRepository<TimeLoadHistory, Int> {
}