package com.jtmnetwork.play.entrypoint.controller

import com.jtmnetwork.play.core.util.TestUtil
import com.jtmnetwork.play.data.service.TransactionService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(TransactionController::class)
@AutoConfigureWebTestClient
class TransactionControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var transactionService: TransactionService

    private val id = UUID.randomUUID()
    private val trans = TestUtil.createTransaction(id)

    @Test
    fun postTransaction() {
        `when`(transactionService.insertTransaction(anyOrNull())).thenReturn(Mono.just(trans))

        testClient.post()
            .uri("/transaction")
            .bodyValue(trans)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(transactionService, times(1)).insertTransaction(anyOrNull())
        verifyNoMoreInteractions(transactionService)
    }

    @Test
    fun putTransaction() {
        `when`(transactionService.updateTransaction(anyOrNull())).thenReturn(Mono.just(trans))

        testClient.put()
            .uri("/transaction")
            .bodyValue(trans)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(transactionService, times(1)).updateTransaction(anyOrNull())
        verifyNoMoreInteractions(transactionService)
    }

    @Test
    fun getTransaction() {
        `when`(transactionService.getTransaction(anyOrNull())).thenReturn(Mono.just(trans))

        testClient.get()
            .uri("/transaction/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(transactionService, times(1)).getTransaction(anyOrNull())
        verifyNoMoreInteractions(transactionService)
    }

    @Test
    fun getTransactions() {
        `when`(transactionService.getTransactions()).thenReturn(Flux.just(trans))

        testClient.get()
            .uri("/transaction/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(id.toString())

        verify(transactionService, times(1)).getTransactions()
        verifyNoMoreInteractions(transactionService)
    }

    @Test
    fun deleteTransaction() {
        `when`(transactionService.removeTransactions(anyOrNull())).thenReturn(Mono.just(trans))

        testClient.delete()
            .uri("/transaction/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(transactionService, times(1)).removeTransactions(anyOrNull())
        verifyNoMoreInteractions(transactionService)
    }
}