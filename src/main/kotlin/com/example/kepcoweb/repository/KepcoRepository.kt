package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.Kepco
import com.example.kepcoweb.domain.KepcoHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface KepcoRepository : JpaRepository<Kepco, Int> {
}