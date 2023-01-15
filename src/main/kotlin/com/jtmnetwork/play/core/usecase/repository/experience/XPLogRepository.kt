package com.jtmnetwork.play.core.usecase.repository.experience

import com.jtmnetwork.play.core.domain.constant.experience.ExperienceAction
import com.jtmnetwork.play.core.domain.entity.experience.XPLog
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface XPLogRepository: ReactiveMongoRepository<XPLog, Int> {

    fun findByPlayer(player: UUID): Flux<XPLog>

    fun findByPlayerAndAction(player: UUID, action: ExperienceAction): Flux<XPLog>
}