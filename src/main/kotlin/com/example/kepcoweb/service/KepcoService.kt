package com.example.kepcoweb.service

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
                KepcoDto(
                    id = it.id,
                    useVal = it.useVal,
                    gb1 = it.gb1,
                    gb2 = it.gb2,
                    selVal = it.selVal,
                    baseFee = it.baseFee,
                    loadVal = it.loadVal,
                    suf = it.suf,
                    faf = it.faf,
                    wif = it.wif,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
    }
}