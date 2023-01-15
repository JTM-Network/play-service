package com.jtmnetwork.play.core.usecase.repository.economy

import com.jtmnetwork.play.core.domain.entity.economy.Wallet
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

@DataMongoTest
@RunWith(SpringRunner::class)
class WalletIntegrationTest {

    @Autowired
    lateinit var walletRepository: WalletRepository

    private val id = UUID.randomUUID().toString()
    private val wallet = TestUtil.createWallet(id)

    @Before
    fun setup() {
        walletRepository.deleteAll().block()
    }

    @Test
    fun save_shouldReturnWallet() {
        val returned = walletRepository.save(wallet)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnWallet() {
        walletRepository.save(wallet).block()

        val returned = walletRepository.findById(id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmpty() {
        val returned = walletRepository.findById(id)

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnWallets() {
        val secondId = UUID.randomUUID().toString()

        walletRepository.save(wallet).block()
        walletRepository.save(Wallet(id = secondId)).block()

        val returned = walletRepository.findAll()

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .assertNext { assertThat(it.id).isEqualTo(secondId) }
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnEmpty() {
        val returned = walletRepository.findAll()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun deleteById_shouldDeleteWallet() {
        walletRepository.save(wallet).block()

        val exists = walletRepository.existsById(id).block()

        assertNotNull(exists)
        if (exists != null) assertTrue(exists)

        val deleted = walletRepository.deleteById(id)

        StepVerifier.create(deleted)
            .verifyComplete()

        val returned = walletRepository.existsById(id).block()

        assertNotNull(returned)
        if (returned != null) assertFalse(returned)
    }
}