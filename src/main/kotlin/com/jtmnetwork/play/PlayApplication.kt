package com.jtmnetwork.play

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
open class PlayApplication

fun main(args: Array<String>) {
    runApplication<PlayApplication>(*args)
}