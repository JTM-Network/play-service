package com.jtmnetwork.play.data.service.experience

import com.jtmnetwork.play.core.domain.exception.log.LogNotFound
import com.jtmnetwork.play.core.usecase.repository.experience.XPLogRepository
import com.jtmnetwork.play.core.util.TestUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class XPLogServiceUnitTest {

    private val logRepository: XPLogRepository = mock()
    private val logService = XPLogService(logRepository)

    private val id = 1
    private val log = TestUtil.createXPLog(id)

    @Test
    fun createLog_shouldReturnInsertedLog() {
        `when`(logRepository.save(anyOrNull())).thenReturn(Mono.just(log))

        val returned = logService.createLog(log)

        verify(logRepository, times(1)).save(anyOrNull())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPLog(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun getLog_shouldThrowNotFound() {
        `when`(logRepository.findById(anyInt())).thenReturn(Mono.empty())

        val returned = logService.getLog(id)

        verify(logRepository, times(1)).findById(anyInt())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .expectError(LogNotFound::class.java)
            .verify()
    }

    @Test
    fun getLog_shouldReturnLog() {
        `when`(logRepository.findById(anyInt())).thenReturn(Mono.just(log))

        val returned = logService.getLog(id)

        verify(logRepository, times(1)).findById(anyInt())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPLog(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun getLogs_shouldReturnLogs() {
        `when`(logRepository.findAll()).thenReturn(Flux.just(log))

        val returned = logService.getLogs()

        verify(logRepository, times(1)).findAll()
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPLog(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun removeLog_shouldThrowNotFound() {
        `when`(logRepository.findById(anyInt())).thenReturn(Mono.empty())

        val returned = logService.removeLog(id)

        verify(logRepository, times(1)).findById(anyInt())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .expectError(LogNotFound::class.java)
            .verify()
    }

    @Test
    fun removeLog_shouldReturnRemovedLog() {
        `when`(logRepository.findById(anyInt())).thenReturn(Mono.just(log))
        `when`(logRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = logService.removeLog(id)

        verify(logRepository, times(1)).findById(anyInt())
        verifyNoMoreInteractions(logRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPLog(it.id, it) }
            .verifyComplete()
    }
}