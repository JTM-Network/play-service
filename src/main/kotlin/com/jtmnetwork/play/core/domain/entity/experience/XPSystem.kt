package com.jtmnetwork.play.core.domain.entity.experience

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("experience_systems")
data class XPSystem(@Id val id: UUID, var name: String, var constant: Double, var maxLevel: Int, val created: Long)