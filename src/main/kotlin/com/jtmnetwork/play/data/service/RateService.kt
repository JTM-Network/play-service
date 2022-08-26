package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.entity.ExchangeRate
import com.jtmnetwork.play.core.usecase.repository.ExchangeRateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RateService @Autowired constructor(private val rateRepository: ExchangeRateRepository) {

    fun insertRate(rate: ExchangeRate): Mono<ExchangeRate> = Mono.empty()

    fun updateRate(rate: ExchangeRate): Mono<ExchangeRate> = Mono.empty()

    fun getRate(): Mono<ExchangeRate> = Mono.empty()

    fun getRates(): Flux<ExchangeRate> = Flux.empty()

    fun removeRate(): Mono<ExchangeRate> = Mono.empty()
}