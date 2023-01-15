package com.jtmnetwork.play.core.domain.entity.economy

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("currencies")
data class Currency(@Id val id: UUID = UUID.randomUUID(),
                    var name: String = "",
                    var abbreviation: String = "",
                    var symbol: String = "",
                    var main: Boolean = false,
                    val created: Long = System.currentTimeMillis())