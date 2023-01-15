package com.jtmnetwork.play.entrypoint.controller.economy

import com.jtmnetwork.play.core.util.TestUtil
import com.jtmnetwork.play.data.service.economy.RateService
import com.jtmnetwork.play.entrypoint.controller.economy.RateController
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
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(RateController::class)
@AutoConfigureWebTestClient
class RateControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var rateService: RateService

    private val id = UUID.randomUUID()
    private val created = TestUtil.createExchangeRate(id)

    @Test
    fun postRate() {
        `when`(rateService.insertRate(anyOrNull())).thenReturn(Mono.just(created))

        testClient.post()
            .uri("/rate")
            .bodyValue(created)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(rateService, times(1)).insertRate(anyOrNull())
        verifyNoMoreInteractions(rateService)
    }

    @Test
    fun putRate() {
        `when`(rateService.updateRate(anyOrNull())).thenReturn(Mono.just(created))

        testClient.put()
            .uri("/rate")
            .bodyValue(created)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(rateService, times(1)).updateRate(anyOrNull())
        verifyNoMoreInteractions(rateService)
    }

    @Test
    fun getRate() {
        `when`(rateService.getRate(anyOrNull())).thenReturn(Mono.just(created))

        testClient.get()
            .uri("/rate/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(rateService, times(1)).getRate(anyOrNull())
        verifyNoMoreInteractions(rateService)
    }

    @Test
    fun getRates() {
        `when`(rateService.getRates()).thenReturn(Flux.just(created))

        testClient.get()
            .uri("/rate/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(id.toString())

        verify(rateService, times(1)).getRates()
        verifyNoMoreInteractions(rateService)
    }

    @Test
    fun deleteRate() {
        `when`(rateService.removeRate(anyOrNull())).thenReturn(Mono.just(created))

        testClient.delete()
            .uri("/rate/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())

        verify(rateService, times(1)).removeRate(anyOrNull())
        verifyNoMoreInteractions(rateService)
    }
}