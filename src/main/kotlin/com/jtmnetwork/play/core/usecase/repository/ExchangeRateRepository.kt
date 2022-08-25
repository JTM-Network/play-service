package com.jtmnetwork.play.core.usecase.repository

import com.jtmnetwork.play.core.domain.entity.ExchangeRate
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface ExchangeRateRepository: ReactiveMongoRepository<ExchangeRate, UUID> {

    fun findBySymbol(symbol: String): Mono<ExchangeRate>
}