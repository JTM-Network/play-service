package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.entity.Transaction
import com.jtmnetwork.play.core.usecase.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TransactionService @Autowired constructor(private val transactionRepository: TransactionRepository) {

    fun insertTransaction(transaction: Transaction): Mono<Transaction> = Mono.empty()

    fun updateTransaction(transaction: Transaction): Mono<Transaction> = Mono.empty()

    fun getTransaction(): Mono<Transaction> = Mono.empty()

    fun getTransactions(): Flux<Transaction> = Flux.empty()

    fun removeTransactions(): Mono<Transaction> = Mono.empty()
}