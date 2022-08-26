package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.entity.Currency
import com.jtmnetwork.play.core.usecase.repository.CurrencyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CurrencyService @Autowired constructor(private val currencyRepository: CurrencyRepository) {

    fun insertCurrency(currency: Currency): Mono<Currency> = Mono.empty()

    fun updateCurrency(currency: Currency): Mono<Currency> = Mono.empty()

    fun getCurrency(): Mono<Currency> = Mono.empty()

    fun getCurrencies(): Flux<Currency> = Flux.empty()

    fun removeCurrency(): Mono<Currency> = Mono.empty()
}

