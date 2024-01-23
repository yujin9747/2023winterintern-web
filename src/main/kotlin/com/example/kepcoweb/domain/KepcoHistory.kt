package com.example.kepcoweb.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
@Table(name = "electric_rates_history")
class KepcoHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    var id: Int = 0

    @Column(name = "UseVal", nullable = false)
    var useVal: Int = 0

    @Column(name = "GB1")
    var gb1: Int? = 0

    @Column(name = "GB2", length = 10)
    var gb2: String? = ""

    @Column(name = "SelVal")
    var selVal: Int? = 0

    @Column(name = "BaseFee")
    var baseFee: Int? = 0

    @Column(name = "LoadVal")
    var loadVal: Int? = 0

    @Column(name = "SUF", columnDefinition = "decimal(10,1)")
    var suf: Float? = 0f

    @Column(name = "FAF", columnDefinition = "decimal(10,1)")
    var faf: Float? = 0f

    @Column(name = "WIF", columnDefinition = "decimal(10,1)")
    var wif: Float? = 0f

    @Column(name = "appliedPeriod", columnDefinition = "datetime(6)")
    var appliedPeriod: LocalDateTime? = null

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false, columnDefinition = "datetime(6)")
    var updatedAt: LocalDateTime? = LocalDateTime.now()

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, columnDefinition = "datetime(6)")
    var createdAt: LocalDateTime? = LocalDateTime.now()
}