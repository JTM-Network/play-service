package com.jtmnetwork.play.entrypoint.controller

import com.jtmnetwork.play.core.domain.entity.ExchangeRate
import com.jtmnetwork.play.data.service.RateService
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
import java.util.UUID

@RestController
@RequestMapping("/rate")
class RateController @Autowired constructor(private val rateService: RateService) {

    @PostMapping
    fun postRate(@RequestBody rate: ExchangeRate): Mono<ExchangeRate> = Mono.empty()

    @PutMapping
    fun putRate(@RequestBody rate: ExchangeRate): Mono<ExchangeRate> = Mono.empty()

    @GetMapping("/{id}")
    fun getRate(@PathVariable id: UUID): Mono<ExchangeRate> = Mono.empty()

    @GetMapping("/all")
    fun getRates(): Flux<ExchangeRate> = Flux.empty()

    @DeleteMapping("/{id}")
    fun deleteRate(@PathVariable id: UUID): Mono<ExchangeRate> = Mono.empty()
}