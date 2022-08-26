package com.jtmnetwork.play.entrypoint.controller

import com.jtmnetwork.play.core.domain.entity.Wallet
import com.jtmnetwork.play.data.service.WalletService
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

@RestController
@RequestMapping("/wallet")
class WalletController @Autowired constructor(private val walletService: WalletService) {

    @PostMapping
    fun postWallet(@RequestBody wallet: Wallet): Mono<Wallet> = Mono.empty()

    @PutMapping
    fun putWallet(@RequestBody wallet: Wallet): Mono<Wallet> = Mono.empty()

    @GetMapping("/{id}")
    fun getWallet(@PathVariable id: String): Mono<Wallet> = Mono.empty()

    @GetMapping("/all")
    fun getWallets(): Flux<Wallet> = Flux.empty()

    @DeleteMapping("/{id}")
    fun deleteWallet(@PathVariable id: String): Mono<Wallet> = Mono.empty()
}