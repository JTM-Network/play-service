package com.jtmnetwork.play.core.usecase.repository.experience

import com.jtmnetwork.play.core.domain.entity.experience.XPProfile
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface XPProfileRepository: ReactiveMongoRepository<XPProfile, UUID> {

    fun findByName(name: String): Mono<XPProfile>
}