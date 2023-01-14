package com.jtmnetwork.play.core.usecase.repository.economy

import com.jtmnetwork.play.core.domain.entity.economy.Transaction
import com.jtmnetwork.play.core.usecase.repository.economy.TransactionRepository
import com.jtmnetwork.play.core.util.TestUtil
import com.mongodb.internal.connection.tlschannel.util.Util.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
@DataMongoTest
class TransactionIntegrationTest {

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    private val id = UUID.randomUUID()
    private val transaction = TestUtil.createTransaction(id)

    @Before
    fun setup() {
        transactionRepository.deleteAll().block()
    }

    @Test
    fun save_shouldReturnTrans() {
        val returned = transactionRepository.save(transaction)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnTrans() {
        transactionRepository.save(transaction).block()

        val returned = transactionRepository.findById(id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmpty() {
        val returned = transactionRepository.findById(id)

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnTransactions() {
        val secondId = UUID.randomUUID()

        transactionRepository.save(transaction).block()
        transactionRepository.save(Transaction(secondId)).block()

        val returned = transactionRepository.findAll()

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertTransaction(id, it) }
            .assertNext { assertThat(it.id).isEqualTo(secondId) }
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnEmpty() {
        val returned = transactionRepository.findAll()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun deleteById_shouldDeleteTrans() {
        transactionRepository.save(transaction).block()

        val exists = transactionRepository.existsById(id).block()

        assertNotNull(exists)
        if (exists != null) assertTrue(exists)

        val operation = transactionRepository.deleteById(id)

        StepVerifier.create(operation)
            .verifyComplete()

        val returned = transactionRepository.existsById(id).block()

        assertNotNull(returned)
        if (returned != null) assertFalse(returned)
    }
}