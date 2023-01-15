package com.jtmnetwork.play.entrypoint.controller.experience

import com.jtmnetwork.play.core.util.TestUtil
import com.jtmnetwork.play.data.service.experience.XPSystemService
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
@WebFluxTest(XPSystemController::class)
@AutoConfigureWebTestClient
class XPSystemControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var systemService: XPSystemService

    private val id = UUID.randomUUID()
    private val system = TestUtil.createXPSystem(id)

    @Test
    fun postSystem_shouldReturnSystem() {
        `when`(systemService.createSystem(anyOrNull())).thenReturn(Mono.just(system))

        testClient.post()
            .uri("/exp-system")
            .bodyValue(system)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Skills")
            .jsonPath("$.constant").isEqualTo(0.15)
            .jsonPath("$.maxLevel").isEqualTo(100)

        verify(systemService, times(1)).createSystem(anyOrNull())
        verifyNoMoreInteractions(systemService)
    }

    @Test
    fun putSystem_shouldReturnSystem() {
        `when`(systemService.updateSystem(anyOrNull())).thenReturn(Mono.just(system))

        testClient.put()
            .uri("/exp-system")
            .bodyValue(system)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Skills")
            .jsonPath("$.constant").isEqualTo(0.15)
            .jsonPath("$.maxLevel").isEqualTo(100)

        verify(systemService, times(1)).updateSystem(anyOrNull())
        verifyNoMoreInteractions(systemService)
    }

    @Test
    fun getSystem_shouldReturnSystem() {
        `when`(systemService.getSystem(anyOrNull())).thenReturn(Mono.just(system))

        testClient.get()
            .uri("/exp-system/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Skills")
            .jsonPath("$.constant").isEqualTo(0.15)
            .jsonPath("$.maxLevel").isEqualTo(100)

        verify(systemService, times(1)).getSystem(anyOrNull())
        verifyNoMoreInteractions(systemService)
    }

    @Test
    fun getSystems() {
        `when`(systemService.getSystems()).thenReturn(Flux.just(system))

        testClient.get()
            .uri("/exp-system/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(id.toString())
            .jsonPath("$[0].name").isEqualTo("Skills")
            .jsonPath("$[0].constant").isEqualTo(0.15)
            .jsonPath("$[0].maxLevel").isEqualTo(100)

        verify(systemService, times(1)).getSystems()
        verifyNoMoreInteractions(systemService)
    }

    @Test
    fun deleteSystem_shouldReturnSystem() {
        `when`(systemService.removeSystem(anyOrNull())).thenReturn(Mono.just(system))

        testClient.delete()
            .uri("/exp-system/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Skills")
            .jsonPath("$.constant").isEqualTo(0.15)
            .jsonPath("$.maxLevel").isEqualTo(100)

        verify(systemService, times(1)).removeSystem(anyOrNull())
        verifyNoMoreInteractions(systemService)
    }
}