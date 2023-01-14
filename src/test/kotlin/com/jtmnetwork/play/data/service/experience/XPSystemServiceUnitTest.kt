package com.jtmnetwork.play.data.service.experience

import com.jtmnetwork.play.core.domain.exception.system.SystemFound
import com.jtmnetwork.play.core.domain.exception.system.SystemNotFound
import com.jtmnetwork.play.core.usecase.repository.experience.XPSystemRepository
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
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class XPSystemServiceUnitTest {

    private val systemRepository: XPSystemRepository = mock()
    private val systemService = XPSystemService(systemRepository)

    private val id = UUID.randomUUID()
    private val system = TestUtil.createXPSystem(id)

    @Test
    fun createSystem_shouldThrowFound() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(system))

        val returned = systemService.createSystem(system)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .expectError(SystemFound::class.java)
            .verify()
    }

    @Test
    fun createSystem_shouldReturnInsertedSystem() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())
        `when`(systemRepository.save(anyOrNull())).thenReturn(Mono.just(system))

        val returned = systemService.createSystem(system)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun updateSystem_shouldThrowNotFound() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = systemService.updateSystem(system)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .expectError(SystemNotFound::class.java)
            .verify()
    }

    @Test
    fun updateSystem_shouldReturnUpdatedSystem() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(system))
        `when`(systemRepository.save(anyOrNull())).thenReturn(Mono.just(system))

        val returned = systemService.updateSystem(system)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun getSystem_shouldThrowNotFound() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = systemService.getSystem(id)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .expectError(SystemNotFound::class.java)
            .verify()
    }

    @Test
    fun getSystem_shouldReturnSystem() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(system))

        val returned = systemService.getSystem(id)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun getSystems() {
        `when`(systemRepository.findAll()).thenReturn(Flux.just(system))

        val returned = systemService.getSystems()

        verify(systemRepository, times(1)).findAll()
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(it.id, it) }
            .verifyComplete()
    }

    @Test
    fun removeSystem_shouldThrowNotFound() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = systemService.removeSystem(id)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .expectError(SystemNotFound::class.java)
            .verify()
    }

    @Test
    fun removeSystem_shouldReturnDeletedSystem() {
        `when`(systemRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(system))
        `when`(systemRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = systemService.removeSystem(id)

        verify(systemRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(systemRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(it.id, it) }
            .verifyComplete()
    }
}