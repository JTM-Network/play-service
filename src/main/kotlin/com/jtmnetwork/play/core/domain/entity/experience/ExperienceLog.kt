package com.jtmnetwork.play.core.domain.entity.experience

import com.jtmnetwork.play.core.domain.constant.experience.ExperienceAction
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("experience_logs")
data class ExperienceLog(@Id val id: Int, val player: UUID, val action: ExperienceAction, val amount: Int, val metadata: String, val created: Long)