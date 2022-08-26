package com.jtmnetwork.play.core.domain.exception.exchangerate

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FOUND, reason = "Exchange rate already found with that pair.")
class RateFound: RuntimeException()