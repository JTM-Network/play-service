package com.jtmnetwork.play.core.domain.entity

import com.jtmnetwork.play.core.domain.constant.TransactionType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("transactions")
data class Transaction(@Id val id: UUID = UUID.randomUUID(),
                       val type: TransactionType = TransactionType.IN,
                       val sender: UUID? = UUID.randomUUID(),
                       val receiver: UUID? = UUID.randomUUID(),
                       val currency: UUID = UUID.randomUUID(),
                       val amount: Double = 0.0,
                       val previous_balance: Double = 0.0,
                       val new_balance: Double = 0.0,
                       val created: Long = System.currentTimeMillis())
