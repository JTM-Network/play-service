package com.jtmnetwork.play.core.usecase.repository.economy

import com.jtmnetwork.play.core.domain.entity.economy.ExchangeRate
import com.jtmnetwork.play.core.usecase.repository.economy.ExchangeRateRepository
import com.jtmnetwork.play.core.util.TestUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
@DataMongoTest
class ExchangeRateIntegrationTest {

    @Autowired
    lateinit var exchangeRateRepository: ExchangeRateRepository

    private val id = UUID.randomUUID()
    private val rate = TestUtil.createExchangeRate(id)

    @Before
    fun setup() {
        exchangeRateRepository.deleteAll().block()
    }

    @Test
    fun save_shouldReturnRate() {
        val returned = exchangeRateRepository.save(rate)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnRate() {
        exchangeRateRepository.save(rate).block()

        val returned = exchangeRateRepository.findById(id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmpty() {
        val returned = exchangeRateRepository.findById(id)

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnRates() {
        val secondId = UUID.randomUUID()

        exchangeRateRepository.save(rate).block()
        exchangeRateRepository.save(ExchangeRate(id = secondId)).block()

        val returned = exchangeRateRepository.findAll()

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertExchangeRate(id, it) }
            .assertNext { assertThat(it.id).isEqualTo(secondId) }
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnEmpty() {
        val returned = exchangeRateRepository.findAll()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun deleteById_shouldDeleteRate() {
        exchangeRateRepository.save(rate).block()

        val exists = exchangeRateRepository.existsById(id).block()

        assertNotNull(exists)
        if (exists != null) assertTrue(exists)

        val deleted = exchangeRateRepository.deleteById(id)

        StepVerifier.create(deleted)
            .verifyComplete()

        val returned = exchangeRateRepository.existsById(id).block()

        assertNotNull(returned)
        if (returned != null) assertFalse(returned)
    }
}