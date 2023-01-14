package com.jtmnetwork.play.data.service.economy

import com.jtmnetwork.play.core.domain.entity.economy.Currency
import com.jtmnetwork.play.core.domain.exception.currency.CurrencyFound
import com.jtmnetwork.play.core.domain.exception.currency.CurrencyNotFound
import com.jtmnetwork.play.core.usecase.repository.economy.CurrencyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class CurrencyService @Autowired constructor(private val currencyRepository: CurrencyRepository) {

    fun insertCurrency(currency: Currency): Mono<Currency> {
        return currencyRepository.findByName(currency.name)
            .flatMap<Currency> { Mono.error(CurrencyFound()) }
            .switchIfEmpty(Mono.defer { currencyRepository.save(currency) })
    }

    fun updateCurrency(currency: Currency): Mono<Currency> {
        return currencyRepository.findById(currency.id)
            .switchIfEmpty(Mono.defer { Mono.error(CurrencyNotFound()) })
            .flatMap { currencyRepository.save(currency) }
    }

    fun getCurrency(id: UUID): Mono<Currency> {
        return currencyRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(CurrencyNotFound()) })
    }

    fun getCurrencies(): Flux<Currency> = currencyRepository.findAll()

    fun removeCurrency(id: UUID): Mono<Currency> {
        return currencyRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(CurrencyNotFound()) })
            .flatMap { currencyRepository.delete(it).thenReturn(it) }
    }
}

