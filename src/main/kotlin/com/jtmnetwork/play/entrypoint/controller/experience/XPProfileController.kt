package com.jtmnetwork.play.entrypoint.controller.experience

import com.jtmnetwork.play.core.domain.entity.experience.ExperienceProfile
import com.jtmnetwork.play.data.service.experience.XPProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/exp-profile")
class XPProfileController @Autowired constructor(private val profileService: XPProfileService) {

    @PostMapping
    fun postProfile(@RequestBody profile: ExperienceProfile): Mono<ExperienceProfile> = profileService.insertProfile(profile)

    @PutMapping
    fun putProfile(@RequestBody profile: ExperienceProfile): Mono<ExperienceProfile> = profileService.updateProfile(profile)

    @GetMapping("/{id}")
    fun getProfile(@PathVariable id: UUID): Mono<ExperienceProfile> = profileService.getProfile(id)

    @GetMapping("/all")
    fun getProfiles(): Flux<ExperienceProfile> = profileService.getProfiles()

    @DeleteMapping("/{id}")
    fun deleteProfile(@PathVariable id: UUID): Mono<ExperienceProfile> = profileService.removeProfile(id)
}