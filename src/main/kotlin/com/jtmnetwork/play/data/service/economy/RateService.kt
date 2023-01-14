package com.jtmnetwork.play.data.service.economy

import com.jtmnetwork.play.core.domain.entity.economy.ExchangeRate
import com.jtmnetwork.play.core.domain.exception.exchangerate.RateFound
import com.jtmnetwork.play.core.domain.exception.exchangerate.RateNotFound
import com.jtmnetwork.play.core.usecase.repository.economy.ExchangeRateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class RateService @Autowired constructor(private val rateRepository: ExchangeRateRepository) {

    fun insertRate(rate: ExchangeRate): Mono<ExchangeRate> {
        return rateRepository.findBySymbol(rate.symbol)
            .flatMap<ExchangeRate> { Mono.error(RateFound()) }
            .switchIfEmpty(Mono.defer { rateRepository.save(rate) })
    }

    fun updateRate(rate: ExchangeRate): Mono<ExchangeRate> {
        return rateRepository.findById(rate.id)
            .switchIfEmpty(Mono.defer { Mono.error(RateNotFound()) })
            .flatMap { rateRepository.save(rate) }
    }

    fun getRate(id: UUID): Mono<ExchangeRate> {
        return rateRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(RateNotFound()) })
    }

    fun getRates(): Flux<ExchangeRate> = rateRepository.findAll()

    fun removeRate(id: UUID): Mono<ExchangeRate> {
        return rateRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(RateNotFound()) })
            .flatMap { rateRepository.delete(it).thenReturn(it) }
    }
}