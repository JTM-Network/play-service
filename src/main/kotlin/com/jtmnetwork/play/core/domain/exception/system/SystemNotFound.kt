package com.jtmnetwork.play.core.domain.exception.system

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "System not found.")
class SystemNotFound: RuntimeException()