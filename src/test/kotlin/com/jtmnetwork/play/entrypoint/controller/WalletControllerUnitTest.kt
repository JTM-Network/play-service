package com.jtmnetwork.play.entrypoint.controller

import com.jtmnetwork.play.core.util.TestUtil
import com.jtmnetwork.play.data.service.WalletService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
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
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(WalletController::class)
@AutoConfigureWebTestClient
class WalletControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var walletService: WalletService

    private val id = UUID.randomUUID().toString()
    private val created = TestUtil.createWallet(id)

    @Test
    fun postWallet() {
        `when`(walletService.insertWallet(anyOrNull())).thenReturn(Mono.just(created))

        testClient.post()
            .uri("/wallet")
            .bodyValue(created)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)

        verify(walletService, times(1)).insertWallet(anyOrNull())
        verifyNoMoreInteractions(walletService)
    }

    @Test
    fun putWallet() {
        `when`(walletService.updateWallet(anyOrNull())).thenReturn(Mono.just(created))

        testClient.put()
            .uri("/wallet")
            .bodyValue(created)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)

        verify(walletService, times(1)).updateWallet(anyOrNull())
        verifyNoMoreInteractions(walletService)
    }

    @Test
    fun getWallet() {
        `when`(walletService.getWallet(anyString())).thenReturn(Mono.just(created))

        testClient.get()
            .uri("/wallet/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)

        verify(walletService, times(1)).getWallet(anyString())
        verifyNoMoreInteractions(walletService)
    }

    @Test
    fun getWallets() {
        `when`(walletService.getWallets()).thenReturn(Flux.just(created))

        testClient.get()
            .uri("/wallet/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(id)

        verify(walletService, times(1)).getWallets()
        verifyNoMoreInteractions(walletService)
    }

    @Test
    fun deleteWallet() {
        `when`(walletService.removeWallet(anyString())).thenReturn(Mono.just(created))

        testClient.delete()
            .uri("/wallet/${UUID.randomUUID().toString()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)

        verify(walletService, times(1)).removeWallet(anyString())
        verifyNoMoreInteractions(walletService)
    }
}