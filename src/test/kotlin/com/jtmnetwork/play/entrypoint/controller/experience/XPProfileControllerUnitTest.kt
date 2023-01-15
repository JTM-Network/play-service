package com.jtmnetwork.play.entrypoint.controller.experience

import com.jtmnetwork.play.core.util.TestUtil
import com.jtmnetwork.play.data.service.experience.XPProfileService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(controllers = [XPProfileController::class])
@AutoConfigureWebTestClient
class XPProfileControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var profileService: XPProfileService

    private val id = UUID.randomUUID()
    private val profile = TestUtil.createXPProfile(id)

    @Test
    fun postProfile_shouldReturnProfile() {
        `when`(profileService.insertProfile(anyOrNull())).thenReturn(Mono.just(profile))

        testClient.post()
            .uri("/exp-profile")
            .bodyValue(profile)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Joe")

        verify(profileService, times(1)).insertProfile(anyOrNull())
        verifyNoMoreInteractions(profileService)
    }

    @Test
    fun putProfile_shouldReturnProfile() {
        `when`(profileService.updateProfile(anyOrNull())).thenReturn(Mono.just(profile))

        testClient.put()
            .uri("/exp-profile")
            .bodyValue(profile)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Joe")

        verify(profileService, times(1)).updateProfile(anyOrNull())
        verifyNoMoreInteractions(profileService)
    }

    @Test
    fun getProfile_shouldReturnProfile() {
        `when`(profileService.getProfile(id)).thenReturn(Mono.just(profile))

        testClient.get()
            .uri("/exp-profile/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Joe")

        verify(profileService, times(1)).getProfile(id)
        verifyNoMoreInteractions(profileService)
    }

    @Test
    fun getProfiles() {
        `when`(profileService.getProfiles()).thenReturn(Flux.just(profile))

        testClient.get()
            .uri("/exp-profile/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].id").isEqualTo(id.toString())
            .jsonPath("$[0].name").isEqualTo("Joe")

        verify(profileService, times(1)).getProfiles()
        verifyNoMoreInteractions(profileService)
    }

    @Test
    fun deleteProfile_shouldReturnProfile() {
        `when`(profileService.removeProfile(anyOrNull())).thenReturn(Mono.just(profile))

        testClient.delete()
            .uri("/exp-profile/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id.toString())
            .jsonPath("$.name").isEqualTo("Joe")

        verify(profileService, times(1)).removeProfile(id)
        verifyNoMoreInteractions(profileService)
    }
}