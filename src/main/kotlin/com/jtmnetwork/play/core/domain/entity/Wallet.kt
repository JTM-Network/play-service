package com.jtmnetwork.play.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.collections.HashMap

@Document("wallets")
data class Wallet(@Id val id: String = "",
                  val name: String = "",
                  val balances: MutableMap<UUID, Double> = HashMap(),
                  val created: Long = System.currentTimeMillis())
