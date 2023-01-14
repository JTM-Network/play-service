package com.jtmnetwork.play.data.service.experience

import com.jtmnetwork.play.core.domain.entity.experience.XPSystem
import com.jtmnetwork.play.core.domain.exception.system.SystemFound
import com.jtmnetwork.play.core.domain.exception.system.SystemNotFound
import com.jtmnetwork.play.core.usecase.repository.experience.XPSystemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class XPSystemService @Autowired constructor(private val systemRepository: XPSystemRepository) {

    /**
     * Create a new experience system
     *
     * @param system the system to create
     *
     * @return the created system
     */
    fun createSystem(system: XPSystem): Mono<XPSystem> {
        return systemRepository.findById(system.id)
            .flatMap<XPSystem> { Mono.error(SystemFound()) }
            .switchIfEmpty(Mono.defer { systemRepository.save(system) })
    }

    /**
     * Update an experience system
     *
     * @param system the system to update
     *
     * @return the updated system
     */
    fun updateSystem(system: XPSystem): Mono<XPSystem> {
        return systemRepository.findById(system.id)
            .switchIfEmpty(Mono.defer { Mono.error(SystemNotFound()) })
            .flatMap { systemRepository.save(system) }
    }

    /**
     * Get an experience system by its id
     *
     * @param id the id of the system
     *
     * @return the system
     */
    fun getSystem(id: UUID): Mono<XPSystem> {
        return systemRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(SystemNotFound()) })
    }

    /**
     * Get all experience systems
     *
     * @return list of all the systems
     */
    fun getSystems(): Flux<XPSystem> {
        return systemRepository.findAll()
    }

    /**
     * Delete an experience system
     *
     * @param id the id of the system
     *
     * @return the deleted system
     */
    fun removeSystem(id: UUID): Mono<XPSystem> {
        return systemRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(SystemNotFound()) })
            .flatMap { systemRepository.delete(it).thenReturn(it) }
    }
}