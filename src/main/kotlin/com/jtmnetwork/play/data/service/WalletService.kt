package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.entity.Wallet
import com.jtmnetwork.play.core.usecase.repository.WalletRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class WalletService @Autowired constructor(private val walletRepository: WalletRepository) {

    fun insertWallet(wallet: Wallet): Mono<Wallet> = Mono.empty()

    fun updateWallet(wallet: Wallet): Mono<Wallet> = Mono.empty()

    fun getWallet(): Mono<Wallet> = Mono.empty()

    fun getWallets(): Flux<Wallet> = Flux.empty()

    fun removeWallet(): Mono<Wallet> = Mono.empty()
}