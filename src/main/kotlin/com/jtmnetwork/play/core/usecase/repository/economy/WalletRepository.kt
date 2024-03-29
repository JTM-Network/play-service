package com.jtmnetwork.play.core.usecase.repository.economy

import com.jtmnetwork.play.core.domain.entity.economy.Wallet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface WalletRepository: ReactiveMongoRepository<Wallet, String> {

    fun findByName(name: String): Mono<Wallet>

    fun findByNameStartsWithIgnoreCaseOrderByNameDesc(name: String): Flux<Wallet>
}