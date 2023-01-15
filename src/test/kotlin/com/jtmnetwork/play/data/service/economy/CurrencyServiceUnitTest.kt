package com.jtmnetwork.play.data.service.economy

import com.jtmnetwork.play.core.domain.entity.economy.Currency
import com.jtmnetwork.play.core.domain.exception.currency.CurrencyFound
import com.jtmnetwork.play.core.domain.exception.currency.CurrencyNotFound
import com.jtmnetwork.play.core.usecase.repository.economy.CurrencyRepository
import com.jtmnetwork.play.core.util.TestUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
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
class CurrencyServiceUnitTest {

    private val currencyRepository: CurrencyRepository = mock()
    private val currencyService = CurrencyService(currencyRepository)

    private val id = UUID.randomUUID()
    private val created = TestUtil.createCurrency(id)

    @Test
    fun insertCurrency_shouldThrowFound() {
        `when`(currencyRepository.findByName(anyString())).thenReturn(Mono.just(created))

        val returned = currencyService.insertCurrency(created)

        verify(currencyRepository, times(1)).findByName(anyString())
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .expectError(CurrencyFound::class.java)
            .verify()
    }

    @Test
    fun insertCurrency_shouldReturnCurrency() {
        `when`(currencyRepository.findByName(anyString())).thenReturn(Mono.empty())
        `when`(currencyRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = currencyService.insertCurrency(created)

        verify(currencyRepository, times(1)).findByName(anyString())
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .verifyComplete()
    }

    @Test
    fun updateCurrency_shouldThrowNotFound() {
        `when`(currencyRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = currencyService.updateCurrency(created)

        verify(currencyRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .expectError(CurrencyNotFound::class.java)
            .verify()
    }

    @Test
    fun updateCurrency_shouldReturnCurrency() {
        `when`(currencyRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(currencyRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = currencyService.updateCurrency(created)

        verify(currencyRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .verifyComplete()
    }

    @Test
    fun getCurrency_shouldThrowNotFound() {
        `when`(currencyRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = currencyService.getCurrency(UUID.randomUUID())

        verify(currencyRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .expectError(CurrencyNotFound::class.java)
            .verify()
    }

    @Test
    fun getCurrency_shouldReturnCurrency() {
        `when`(currencyRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))

        val returned = currencyService.getCurrency(UUID.randomUUID())

        verify(currencyRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .verifyComplete()
    }

    @Test
    fun getCurrencies_shouldReturnTwoCurrencies() {
        val secondId = UUID.randomUUID()

        `when`(currencyRepository.findAll()).thenReturn(Flux.just(created, Currency(secondId)))

        val returned = currencyService.getCurrencies()

        verify(currencyRepository, times(1)).findAll()
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .assertNext { assertThat(it.id).isEqualTo(secondId) }
            .verifyComplete()
    }

    @Test
    fun removeCurrency_shouldThrowNotFound() {
        `when`(currencyRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = currencyService.removeCurrency(UUID.randomUUID())

        verify(currencyRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .expectError(CurrencyNotFound::class.java)
            .verify()
    }

    @Test
    fun removeCurrency_shouldReturnCurrency() {
        `when`(currencyRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(currencyRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = currencyService.removeCurrency(UUID.randomUUID())

        verify(currencyRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(currencyRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .verifyComplete()
    }
}