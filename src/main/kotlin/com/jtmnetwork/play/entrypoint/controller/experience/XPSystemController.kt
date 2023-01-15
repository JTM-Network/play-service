package com.jtmnetwork.play.entrypoint.controller.experience

import com.jtmnetwork.play.core.domain.entity.experience.XPSystem
import com.jtmnetwork.play.data.service.experience.XPSystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RequestMapping("/exp-system")
@RestController
class XPSystemController @Autowired constructor(private val systemService: XPSystemService) {

    @PostMapping
    fun postSystem(@RequestBody system: XPSystem): Mono<XPSystem> = systemService.createSystem(system)

    @PutMapping
    fun putSystem(@RequestBody system: XPSystem): Mono<XPSystem> = systemService.updateSystem(system)

    @GetMapping("/{id}")
    fun getSystem(@PathVariable id: UUID): Mono<XPSystem> = systemService.getSystem(id)

    @GetMapping("/all")
    fun getSystems(): Flux<XPSystem> = systemService.getSystems()

    @DeleteMapping("/{id}")
    fun deleteSystem(@PathVariable id: UUID): Mono<XPSystem> = systemService.removeSystem(id)
}