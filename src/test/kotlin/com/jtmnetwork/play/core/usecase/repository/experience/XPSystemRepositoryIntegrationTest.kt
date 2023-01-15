package com.jtmnetwork.play.core.usecase.repository.experience

import com.jtmnetwork.play.core.util.TestUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.*

@DataMongoTest
@RunWith(SpringRunner::class)
class XPSystemRepositoryIntegrationTest {

    @Autowired
    lateinit var repository: XPSystemRepository

    private val id = UUID.randomUUID()
    private val system = TestUtil.createXPSystem(id)

    @Before
    fun setup() {
        repository.deleteAll().block()
    }

    @Test
    fun save_shouldReturnSystem() {
        val returned = repository.save(system)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmpty() {
        val returned = repository.findById(id)

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnSystem() {
        repository.save(system).block()

        val returned = repository.findById(id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPSystem(id, it) }
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnEmpty() {
        val returned = repository.findAll()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun deleteById_shouldReturnSystem() {
        repository.save(system).block()

        val exists = repository.existsById(id).block()
        if (exists != null) assertTrue(exists)

        val deleted = repository.deleteById(id)

        StepVerifier.create(deleted)
            .expectNextCount(0)
            .verifyComplete()

        val returned = repository.existsById(id).block()

        assertNotNull(returned)
        if (returned != null) assertFalse(returned)
    }
}