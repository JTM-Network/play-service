package com.jtmnetwork.play.entrypoint.controller.economy

import com.jtmnetwork.play.core.domain.entity.economy.Wallet
import com.jtmnetwork.play.data.service.economy.WalletService
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
    fun postWallet(@RequestBody wallet: Wallet): Mono<Wallet> = walletService.insertWallet(wallet)

    @PutMapping
    fun putWallet(@RequestBody wallet: Wallet): Mono<Wallet> = walletService.updateWallet(wallet)

    @GetMapping("/{id}")
    fun getWallet(@PathVariable id: String): Mono<Wallet> = walletService.getWallet(id)

    @GetMapping("/all")
    fun getWallets(): Flux<Wallet> = walletService.getWallets()

    @GetMapping("/search/{name}")
    fun searchByName(@PathVariable name: String): Flux<Wallet> = walletService.searchWallets(name)

    @DeleteMapping("/{id}")
    fun deleteWallet(@PathVariable id: String): Mono<Wallet> = walletService.removeWallet(id)
}