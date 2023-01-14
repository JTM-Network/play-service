package com.jtmnetwork.play.entrypoint.controller.economy

import com.jtmnetwork.play.core.domain.entity.economy.Transaction
import com.jtmnetwork.play.data.service.economy.TransactionService
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
@RequestMapping("/transaction")
class TransactionController @Autowired constructor(private val transactionService: TransactionService) {

    @PostMapping
    fun postTransaction(@RequestBody transaction: Transaction): Mono<Transaction> = transactionService.insertTransaction(transaction)

    @PutMapping
    fun putTransaction(@RequestBody transaction: Transaction): Mono<Transaction> = transactionService.updateTransaction(transaction)

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable id: UUID): Mono<Transaction> = transactionService.getTransaction(id)

    @GetMapping("/all")
    fun getTransactions(): Flux<Transaction> = transactionService.getTransactions()

    @DeleteMapping("/{id}")
    fun deleteTransaction(@PathVariable id: UUID): Mono<Transaction> = transactionService.removeTransactions(id)
}