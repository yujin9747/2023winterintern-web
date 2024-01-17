package com.example.kepcoweb.service

import com.example.kepcoweb.dto.DtoUtils
import com.example.kepcoweb.dto.KepcoDto
import com.example.kepcoweb.repository.KepcoRepository
import org.springframework.stereotype.Service

@Service
class KepcoService(
    val repository: KepcoRepository
) {

    fun getKepco(): List<KepcoDto> {
        return repository.findAll()
            .map {
                DtoUtils.createKepcoDto(it)
            }
    }
}