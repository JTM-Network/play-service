package com.jtmnetwork.play.entrypoint.controller.experience

import com.jtmnetwork.play.core.domain.constant.experience.ExperienceAction
import com.jtmnetwork.play.core.domain.entity.experience.XPLog
import com.jtmnetwork.play.core.util.TestUtil
import com.jtmnetwork.play.data.service.experience.XPLogService
import org.junit.Test
import org.junit.runner.RunWith
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
@WebFluxTest(controllers = [XPLogController::class])
@AutoConfigureWebTestClient
class XPLogControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var logService: XPLogService

    private val id = 1
    private val log = TestUtil.createXPLog(id)

    @Test
    fun postLog_shouldReturnLog() {
        `when`(logService.createLog(anyOrNull())).thenReturn(Mono.just(log))

        testClient.post()
            .uri("/exp-log")
            .bodyValue(log)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.action").isEqualTo(ExperienceAction.ADD.name)
            .jsonPath("$.amount").isEqualTo(10)

        verify(logService, times(1)).createLog(anyOrNull())
        verifyNoMoreInteractions(logService)
    }

    @Test
    fun getLog_shouldReturnLog() {
        `when`(logService.getLog(id)).thenReturn(Mono.just(log))

        testClient.get()
            .uri("/exp-log/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.action").isEqualTo(ExperienceAction.ADD.name)
            .jsonPath("$.amount").isEqualTo(10)

        verify(logService, times(1)).getLog(id)
        verifyNoMoreInteractions(logService)
    }

    @Test
    fun getLogs() {
        `when`(logService.getLogs()).thenReturn(Flux.just(log))

        testClient.get()
            .uri("/exp-log/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(1)
            .jsonPath("$[0].action").isEqualTo(ExperienceAction.ADD.name)
            .jsonPath("$[0].amount").isEqualTo(10)

        verify(logService, times(1)).getLogs()
        verifyNoMoreInteractions(logService)
    }

    @Test
    fun deleteLog_shouldReturnLog() {
        `when`(logService.removeLog(anyInt())).thenReturn(Mono.just(log))

        testClient.delete()
            .uri("/exp-log/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.action").isEqualTo(ExperienceAction.ADD.name)
            .jsonPath("$.amount").isEqualTo(10)

        verify(logService, times(1)).removeLog(anyInt())
        verifyNoMoreInteractions(logService)
    }
}