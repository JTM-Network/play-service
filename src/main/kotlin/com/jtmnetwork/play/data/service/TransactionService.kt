package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.entity.Transaction
import com.jtmnetwork.play.core.domain.exception.transaction.TransactionNotFound
import com.jtmnetwork.play.core.usecase.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class TransactionService @Autowired constructor(private val transactionRepository: TransactionRepository) {

    fun insertTransaction(transaction: Transaction): Mono<Transaction> {
        return transactionRepository.save(transaction)
    }

    fun updateTransaction(transaction: Transaction): Mono<Transaction> {
        return transactionRepository.findById(transaction.id)
            .switchIfEmpty(Mono.defer { Mono.error(TransactionNotFound()) })
            .flatMap { transactionRepository.save(transaction) }
    }

    fun getTransaction(id: UUID): Mono<Transaction> {
        return transactionRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(TransactionNotFound()) })
    }

    fun getTransactions(): Flux<Transaction> = transactionRepository.findAll()

    fun removeTransactions(id: UUID): Mono<Transaction> {
        return transactionRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(TransactionNotFound()) })
            .flatMap { transactionRepository.delete(it).thenReturn(it) }
    }
}