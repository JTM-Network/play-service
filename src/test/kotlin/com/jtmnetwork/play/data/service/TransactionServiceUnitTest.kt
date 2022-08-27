package com.jtmnetwork.play.data.service

import com.jtmnetwork.play.core.domain.exception.transaction.TransactionNotFound
import com.jtmnetwork.play.core.usecase.repository.TransactionRepository
import com.jtmnetwork.play.core.util.TestUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import reactor.core.publisher.Flux

import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class TransactionServiceUnitTest {

    private val transactionRepository: TransactionRepository = mock()
    private val transactionService = TransactionService(transactionRepository)

    private val id = UUID.randomUUID()
    private val created = TestUtil.createTransaction(id)

    @Test
    fun insertTransaction_shouldReturnTrans() {
        `when`(transactionRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = transactionService.insertTransaction(created)

        verify(transactionRepository, times(1)).save(anyOrNull())
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }

    @Test
    fun updateTransaction_shouldThrowNotFound() {
        `when`(transactionRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = transactionService.updateTransaction(created)

        verify(transactionRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .expectError(TransactionNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTransaction_shouldReturnTrans() {
        `when`(transactionRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(transactionRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = transactionService.updateTransaction(created)

        verify(transactionRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }

    @Test
    fun getTransaction_shouldThrowNotFound() {
        `when`(transactionRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = transactionService.getTransaction(id)

        verify(transactionRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .expectError(TransactionNotFound::class.java)
            .verify()
    }

    @Test
    fun getTransaction_shouldReturnTrans() {
        `when`(transactionRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))

        val returned = transactionService.getTransaction(id)

        verify(transactionRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }

    @Test
    fun getTransactions() {
        `when`(transactionRepository.findAll()).thenReturn(Flux.just(created))

        val returned = transactionService.getTransactions()

        verify(transactionRepository, times(1)).findAll()
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }

    @Test
    fun removeTransaction_shouldThrowNotFound() {
        `when`(transactionRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = transactionService.removeTransactions(id)

        verify(transactionRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .expectError(TransactionNotFound::class.java)
            .verify()
    }

    @Test
    fun removeTransaction_shouldReturnTrans() {
        `when`(transactionRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(transactionRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = transactionService.removeTransactions(id)

        verify(transactionRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(transactionRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }
}