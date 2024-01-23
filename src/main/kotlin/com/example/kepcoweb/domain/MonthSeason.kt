package com.example.kepcoweb.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "electric_rates_month_season")
class MonthSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    var id: Int = 0

    @Column(name = "Month", nullable = false)
    var month: Int = 0

    @Column(name = "Season", nullable = false, length = 10)
    var season: String = ""

    @Column(name = "appliedPeriod", columnDefinition = "datetime(6)")
    var appliedPeriod: LocalDateTime? = null

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, columnDefinition = "datetime(6)")
    var createdAt: LocalDateTime? = LocalDateTime.now()

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false, columnDefinition = "datetime(6)")
    var updatedAt: LocalDateTime? = LocalDateTime.now()
}