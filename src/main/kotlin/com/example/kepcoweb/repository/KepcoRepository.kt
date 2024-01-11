package com.example.kepcoweb.repository

import com.example.kepcoweb.domain.Kepco
import org.springframework.data.jpa.repository.JpaRepository

interface KepcoRepository : JpaRepository<Kepco, Int> {
}