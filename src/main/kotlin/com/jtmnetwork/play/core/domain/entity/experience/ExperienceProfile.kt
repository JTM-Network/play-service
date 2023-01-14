package com.jtmnetwork.play.core.domain.entity.experience

import com.jtmnetwork.play.core.domain.model.experience.ExperienceBalance
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.collections.HashMap

@Document("experience_profiles")
data class ExperienceProfile(@Id val id: UUID = UUID.randomUUID(),
                             var name: String,
                             var balances: Map<UUID, ExperienceBalance> = HashMap(),
                             val created: Long)

