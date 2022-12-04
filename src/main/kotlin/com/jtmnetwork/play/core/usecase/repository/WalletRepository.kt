package com.jtmnetwork.play.core.usecase.repository

import com.jtmnetwork.play.core.domain.entity.Wallet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface WalletRepository: ReactiveMongoRepository<Wallet, String> {

    fun findByName(name: String): Mono<Wallet>

    fun findByNameStartsWith(name: String): Flux<Wallet>
}