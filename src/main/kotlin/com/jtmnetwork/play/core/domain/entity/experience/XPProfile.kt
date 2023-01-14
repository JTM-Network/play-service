package com.jtmnetwork.play.core.domain.entity.experience

import com.jtmnetwork.play.core.domain.model.experience.XPBalance
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.collections.HashMap

@Document("experience_profiles")
data class XPProfile(@Id val id: UUID = UUID.randomUUID(),
                     var name: String,
                     var balances: Map<UUID, XPBalance> = HashMap(),
                     val created: Long)

