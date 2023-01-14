package com.jtmnetwork.play.entrypoint.controller.experience

import com.jtmnetwork.play.core.domain.entity.experience.XPLog
import com.jtmnetwork.play.data.service.experience.XPLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RequestMapping("exp-log")
@RestController
class XPLogController @Autowired constructor(private val logService: XPLogService) {

    @PostMapping
    fun postLog(@RequestBody log: XPLog): Mono<XPLog> = logService.createLog(log)

    @GetMapping("/{id}")
    fun getLog(@PathVariable id: Int): Mono<XPLog> = logService.getLog(id)

    @GetMapping("/all")
    fun getLogs(): Flux<XPLog> = logService.getLogs()

    @DeleteMapping("/{id}")
    fun deleteLog(@PathVariable id: Int): Mono<XPLog> = logService.removeLog(id)
}