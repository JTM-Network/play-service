package com.jtmnetwork.play.core.domain.exception.system

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.FOUND, reason = "System already not found.")
class SystemFound: RuntimeException()