package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.entity.Wallet
import com.jtmnetwork.play.core.domain.exception.wallet.WalletFound
import com.jtmnetwork.play.core.domain.exception.wallet.WalletNotFound
import com.jtmnetwork.play.core.usecase.repository.WalletRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class WalletService @Autowired constructor(private val walletRepository: WalletRepository) {

    fun insertWallet(wallet: Wallet): Mono<Wallet> {
        return walletRepository.findById(wallet.id)
            .flatMap<Wallet> { Mono.error(WalletFound()) }
            .switchIfEmpty(Mono.defer { walletRepository.save(wallet) })
    }

    fun updateWallet(wallet: Wallet): Mono<Wallet> {
        return walletRepository.findById(wallet.id)
            .switchIfEmpty(Mono.defer { Mono.error(WalletNotFound()) })
            .flatMap { walletRepository.save(wallet) }
    }

    fun getWallet(id: String): Mono<Wallet> {
        return walletRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(WalletNotFound()) })
    }

    fun getWallets(): Flux<Wallet> = walletRepository.findAll()

    fun searchWallets(name: String): Flux<Wallet> {
        return walletRepository.findByNameStartsWithIgnoreCaseOrderByNameDesc(name)
    }

    fun removeWallet(id: String): Mono<Wallet> {
        return walletRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(WalletNotFound()) })
            .flatMap { walletRepository.delete(it).thenReturn(it) }
    }
}