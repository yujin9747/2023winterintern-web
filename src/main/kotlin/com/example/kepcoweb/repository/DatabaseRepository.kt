package com.example.kepcoweb.repository

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DatabaseRepository (
    @Autowired var em : EntityManager
) {

    fun dropTables() {
        println("drop tables")
        em.createNativeQuery("drop table if exists electric_rates").executeUpdate()
        println("electric_rates dropped")
        em.createNativeQuery("drop table if exists electric_rates_timeLoad").executeUpdate()
        println("electric_rates_timeLoad dropped")
        em.createNativeQuery("drop table if exists electric_rates_month_season").executeUpdate()
        println("electric_rates_month_season dropped")
        em.createNativeQuery("drop table if exists electric_rates_history").executeUpdate()
        println("electric_rates_history dropped")
        em.createNativeQuery("drop table if exists electric_rates_timeLoad_history").executeUpdate()
        println("electric_rates_timeLoad_history dropped")
        em.createNativeQuery("drop table if exists electric_rates_month_season_history").executeUpdate()
        println("electric_rates_month_season_history dropped")
    }

}