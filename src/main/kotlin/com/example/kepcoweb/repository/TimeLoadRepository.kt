package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.TimeLoad
import org.springframework.data.jpa.repository.JpaRepository

interface TimeLoadRepository : JpaRepository<TimeLoad, Int> {
}