package com.jtmnetwork.play.core.usecase.repository.experience

import com.jtmnetwork.play.core.util.TestUtil
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.*

@DataMongoTest
@RunWith(SpringRunner::class)
class XPProfileRepositoryIntegrationTest {

    @Autowired
    lateinit var profileRepository: XPProfileRepository

    private val id = UUID.randomUUID()
    private val profile = TestUtil.createXPProfile(id)

    @Before
    fun setup() {
        profileRepository.deleteAll().block()
    }

    @Test
    fun save_shouldReturnProfile() {
        val returned = profileRepository.save(profile)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmpty() {
        val returned = profileRepository.findById(id)

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnProfile() {
        profileRepository.save(profile).block()

        val returned = profileRepository.findById(id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnEmpty() {
        val returned = profileRepository.findAll()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findAll_shouldReturnProfiles() {
        val secondId = UUID.randomUUID()

        profileRepository.save(profile).block()
        profileRepository.save(TestUtil.createXPProfile(secondId)).block()

        val returned = profileRepository.findAll()

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .assertNext { TestUtil.assertXPProfile(secondId, it) }
            .verifyComplete()
    }

    @Test
    fun deleteById_shouldDeleteProfile() {
        profileRepository.save(profile).block()

        val exists = profileRepository.existsById(id).block()

        assertNotNull(exists)
        if (exists != null) assertTrue(exists)

        val deleted = profileRepository.deleteById(id)

        StepVerifier.create(deleted)
            .verifyComplete()

        val returned = profileRepository.existsById(id).block()

        assertNotNull(returned)
        if (returned != null) assertFalse(returned)
    }
}