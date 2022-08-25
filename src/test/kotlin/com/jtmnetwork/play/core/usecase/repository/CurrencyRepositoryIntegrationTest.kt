package com.jtmnetwork.play.core.usecase.repository

import com.jtmnetwork.play.core.domain.entity.Currency
import com.jtmnetwork.play.core.util.TestUtil
import com.mongodb.internal.connection.tlschannel.util.Util.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
@DataMongoTest
class CurrencyRepositoryIntegrationTest {

    @Autowired
    lateinit var currencyRepository: CurrencyRepository

    private val id = UUID.randomUUID()
    private val currency = TestUtil.createCurrency(id)

    @Before
    fun setup() {
        currencyRepository.deleteAll().block()
    }

    @Test
    fun save_shouldReturnCurrency() {
        val returned = currencyRepository.save(currency)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnCurrency() {
        currencyRepository.save(currency).block()

        val returned = currencyRepository.findById(id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmpty() {
        val returned = currencyRepository.findById(id)

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnCurrencies() {
        val secondId = UUID.randomUUID()

        currencyRepository.save(currency).block()
        currencyRepository.save(Currency(id = secondId, name = "dollar", abbreviation = "USD", symbol = "$")).block()

        val returned = currencyRepository.findAll()

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertCurrency(id, it) }
            .assertNext {
                assertThat(it.id).isEqualTo(secondId)
                assertThat(it.name).isEqualTo("dollar")
                assertThat(it.abbreviation).isEqualTo("USD")
                assertThat(it.symbol).isEqualTo("$")
            }
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnEmpty() {
        val returned = currencyRepository.findAll()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun deleteById_shouldDeleteCurrency() {
        currencyRepository.save(currency).block()

        val exists = currencyRepository.existsById(id).block()

        if (exists != null) assertTrue(exists)

        currencyRepository.deleteById(id).block()

        val returned = currencyRepository.existsById(id).block()

        if (returned != null) assertFalse(returned)
    }
}