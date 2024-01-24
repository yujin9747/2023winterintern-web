package com.example.kepcoweb.repository

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DatabaseRepository (
    @Autowired var em : EntityManager
) {

    fun dropTables() {
        em.createNativeQuery("drop table if exists electric_rates").executeUpdate()
        em.createNativeQuery("drop table if exists electric_rates_timeLoad").executeUpdate()
        em.createNativeQuery("drop table if exists electric_rates_month_season").executeUpdate()
        em.createNativeQuery("drop table if exists electric_rates_history").executeUpdate()
        em.createNativeQuery("drop table if exists electric_rates_timeLoad_history").executeUpdate()
        em.createNativeQuery("drop table if exists electric_rates_month_season_history").executeUpdate()
    }

}