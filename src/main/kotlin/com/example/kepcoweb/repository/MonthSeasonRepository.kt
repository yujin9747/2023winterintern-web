package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.MonthSeason
import com.example.kepcoweb.domain.TimeLoad
import org.springframework.data.jpa.repository.JpaRepository

interface MonthSeasonRepository : JpaRepository<MonthSeason, Int> {
}