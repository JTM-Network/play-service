package com.jtmnetwork.play.core.domain.exception.log

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "XP Log not found.")
class LogNotFound: RuntimeException()