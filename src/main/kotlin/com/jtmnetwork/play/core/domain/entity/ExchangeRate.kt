package com.jtmnetwork.play.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("exchange_rates")
data class ExchangeRate(@Id val id: UUID = UUID.randomUUID(),
                        var currency_from: UUID = UUID.randomUUID(),
                        var currency_to: UUID = UUID.randomUUID(),
                        var symbol: String = "",
                        var rate: Double = 1.0,
                        val created: Long = System.currentTimeMillis())
