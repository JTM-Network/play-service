package com.jtmnetwork.play.core.usecase.repository.economy

import com.jtmnetwork.play.core.domain.entity.economy.Currency
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface CurrencyRepository: ReactiveMongoRepository<Currency, UUID> {

    fun findByName(name: String): Mono<Currency>
}