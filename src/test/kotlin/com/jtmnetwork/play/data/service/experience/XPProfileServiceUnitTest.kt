package com.jtmnetwork.play.data.service.experience

import com.jtmnetwork.play.core.domain.exception.profile.ProfileFound
import com.jtmnetwork.play.core.domain.exception.profile.ProfileNotFound
import com.jtmnetwork.play.core.usecase.repository.experience.XPProfileRepository
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
class XPProfileServiceUnitTest {

    private val profileRepository: XPProfileRepository = mock()
    private val profileService = XPProfileService(profileRepository)

    private val id = UUID.randomUUID()
    private val profile = TestUtil.createXPProfile(id)

    @Test
    fun insertProfile_shouldThrowFound() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(profile))

        val returned = profileService.insertProfile(profile)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .expectError(ProfileFound::class.java)
            .verify()
    }

    @Test
    fun insertProfile_shouldReturnProfile() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())
        `when`(profileRepository.save(anyOrNull())).thenReturn(Mono.just(profile))

        val returned = profileService.insertProfile(profile)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .assertNext { p -> TestUtil.assertXPProfile(id, p) }
            .verifyComplete()
    }

    @Test
    fun updateProfile_shouldThrowNotFound() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = profileService.updateProfile(profile)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .expectError(ProfileNotFound::class.java)
            .verify()
    }

    @Test
    fun updateProfile_shouldReturnProfile() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(profile))
        `when`(profileRepository.save(anyOrNull())).thenReturn(Mono.just(profile))

        val returned = profileService.updateProfile(profile)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .verifyComplete()
    }

    @Test
    fun getProfile_shouldThrowNotFound() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = profileService.getProfile(id)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .expectError(ProfileNotFound::class.java)
            .verify()
    }

    @Test
    fun getProfile_shouldReturnProfile() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(profile))

        val returned = profileService.getProfile(id)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .verifyComplete()
    }

    @Test
    fun getProfiles() {
        `when`(profileRepository.findAll()).thenReturn(Flux.just(profile))

        val returned = profileService.getProfiles()

        verify(profileRepository, times(1)).findAll()
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .verifyComplete()
    }

    @Test
    fun removeProfile_shouldThrowNotFound() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = profileService.removeProfile(id)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .expectError(ProfileNotFound::class.java)
            .verify()
    }

    @Test
    fun removeProfile_shouldReturnProfile() {
        `when`(profileRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(profile))
        `when`(profileRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = profileService.removeProfile(id)

        verify(profileRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(profileRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertXPProfile(id, it) }
            .verifyComplete()
    }
}