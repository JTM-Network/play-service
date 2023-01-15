package com.jtmnetwork.play.data.service.experience

import com.jtmnetwork.play.core.domain.entity.experience.XPProfile
import com.jtmnetwork.play.core.domain.exception.profile.ProfileFound
import com.jtmnetwork.play.core.domain.exception.profile.ProfileNotFound
import com.jtmnetwork.play.core.usecase.repository.experience.XPProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class XPProfileService @Autowired constructor(private val profileRepository: XPProfileRepository) {

    /**
     * Insert the experience profile into the database
     *
     * @param profile the profile to insert
     *
     * @return the inserted profile, throws [ProfileFound] if the profile already exists
     */
    fun insertProfile(profile: XPProfile): Mono<XPProfile> {
        return profileRepository.findById(profile.id)
            .flatMap<XPProfile> { Mono.error(ProfileFound()) }
            .switchIfEmpty(Mono.defer { profileRepository.save(profile) })
    }

    /**
     * Update the experience profile in the database
     *
     * @param profile the profile to update
     *
     * @return the updated profile, throws [ProfileNotFound] if the profile is not found
     */
    fun updateProfile(profile: XPProfile): Mono<XPProfile> {
        return profileRepository.findById(profile.id)
            .switchIfEmpty(Mono.defer { Mono.error(ProfileNotFound()) })
            .flatMap { profileRepository.save(profile) }
    }

    /**
     * Get the experience profile by the player's UUID
     *
     * @param id the player's UUID
     *
     * @return the experience profile, throws [ProfileNotFound] if not found
     */
    fun getProfile(id: UUID): Mono<XPProfile> {
        return profileRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(ProfileNotFound()) })
    }

    /**
     * Get all experience profiles
     *
     * @return all experience profiles
     */
    fun getProfiles(): Flux<XPProfile> = profileRepository.findAll()

    /**
     * Delete the experience profile by the player's UUID
     *
     * @param id the player's UUID
     *
     * @return the deleted experience profile, throws [ProfileNotFound] if not found
     */
    fun removeProfile(id: UUID): Mono<XPProfile> {
        return profileRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(ProfileNotFound()) })
            .flatMap { profileRepository.delete(it).thenReturn(it) }
    }
}