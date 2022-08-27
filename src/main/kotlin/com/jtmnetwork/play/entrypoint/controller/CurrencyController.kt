package com.jtmnetwork.play.entrypoint.controller

import com.jtmnetwork.play.core.domain.entity.Currency
import com.jtmnetwork.play.data.service.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/currency")
class CurrencyController @Autowired constructor(private val currencyService: CurrencyService) {

    @PostMapping
    fun postCurrency(@RequestBody currency: Currency): Mono<Currency> = currencyService.insertCurrency(currency)

    @PutMapping
    fun putCurrency(@RequestBody currency: Currency): Mono<Currency> = currencyService.updateCurrency(currency)

    @GetMapping("/{id}")
    fun getCurrency(@PathVariable id: UUID): Mono<Currency> = currencyService.getCurrency(id)

    @GetMapping("/all")
    fun getCurrencies(): Flux<Currency> = currencyService.getCurrencies()

    @DeleteMapping("/{id}")
    fun deleteCurrency(@PathVariable id: UUID): Mono<Currency> = currencyService.removeCurrency(id)
}