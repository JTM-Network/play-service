package com.jtmnetwork.play.data.service.experience

import com.jtmnetwork.play.core.domain.entity.experience.XPLog
import com.jtmnetwork.play.core.domain.exception.log.LogNotFound
import com.jtmnetwork.play.core.usecase.repository.experience.XPLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class XPLogService @Autowired constructor(private val logRepository: XPLogRepository) {

    /**
     * Create a new experience change log
     *
     * @param log the log to create
     *
     * @return the created log
     */
    fun createLog(log: XPLog): Mono<XPLog> {
        return logRepository.save(log)
    }

    /**
     * Get an experience change log by its id
     *
     * @param id the id of the log
     *
     * @return the log
     */
    fun getLog(id: Int): Mono<XPLog> {
        return logRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(LogNotFound()) })
    }

    /**
     * Get all experience change logs
     *
     * @return list of all the logs
     */
    fun getLogs(): Flux<XPLog> = logRepository.findAll()

    /**
     * Delete an experience change log
     *
     * @param id the id of the log
     *
     * @return the deleted log
     */
    fun removeLog(id: Int): Mono<XPLog> {
        return logRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(LogNotFound()) })
            .flatMap { logRepository.delete(it).thenReturn(it) }
    }
}