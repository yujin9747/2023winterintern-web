package com.example.kepcoweb.service

import com.example.kepcoweb.repository.DatabaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DatabaseService(
    val repository: DatabaseRepository
) {
    fun dropTables() {
        repository.dropTables()
    }
}