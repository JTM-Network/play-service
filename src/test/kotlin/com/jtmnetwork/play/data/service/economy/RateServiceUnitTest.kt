package com.jtmnetwork.play.data.service.economy

import com.jtmnetwork.play.core.domain.entity.economy.ExchangeRate
import com.jtmnetwork.play.core.domain.exception.exchangerate.RateFound
import com.jtmnetwork.play.core.domain.exception.exchangerate.RateNotFound
import com.jtmnetwork.play.core.usecase.repository.economy.ExchangeRateRepository
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
class RateServiceUnitTest {

    private val rateRepository: ExchangeRateRepository = mock()
    private val rateService = RateService(rateRepository)

    private val id = UUID.randomUUID()
    private val created = ExchangeRate(id)

    @Test
    fun insertRate_shouldThrowFound() {
        `when`(rateRepository.findBySymbol(anyString())).thenReturn(Mono.just(created))

        val returned = rateService.insertRate(created)

        verify(rateRepository, times(1)).findBySymbol(anyString())
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .expectError(RateFound::class.java)
            .verify()
    }

    @Test
    fun insertRate_shouldReturnRate() {
        `when`(rateRepository.findBySymbol(anyString())).thenReturn(Mono.empty())
        `when`(rateRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = rateService.insertRate(created)

        verify(rateRepository, times(1)).findBySymbol(anyString())
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }

    @Test
    fun updateRate_shouldThrowNotFound() {
        `when`(rateRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = rateService.updateRate(created)

        verify(rateRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .expectError(RateNotFound::class.java)
            .verify()
    }

    @Test
    fun updateRate_shouldReturnRate() {
        `when`(rateRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(rateRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = rateService.updateRate(created)

        verify(rateRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }

    @Test
    fun getRate_shouldThrowNotFound() {
        `when`(rateRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = rateService.getRate(id)

        verify(rateRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .expectError(RateNotFound::class.java)
            .verify()
    }

    @Test
    fun getRate_shouldReturnRate() {
        `when`(rateRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))

        val returned = rateService.getRate(id)

        verify(rateRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }

    @Test
    fun getRates() {
        `when`(rateRepository.findAll()).thenReturn(Flux.just(created))

        val returned = rateService.getRates()

        verify(rateRepository, times(1)).findAll()
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }

    @Test
    fun removeRate_shouldThrowNotFound() {
        `when`(rateRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = rateService.removeRate(UUID.randomUUID())

        verify(rateRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .expectError(RateNotFound::class.java)
            .verify()
    }

    @Test
    fun removeRate_shouldReturnRate() {
        `when`(rateRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(rateRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = rateService.removeRate(UUID.randomUUID())

        verify(rateRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(rateRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }
}