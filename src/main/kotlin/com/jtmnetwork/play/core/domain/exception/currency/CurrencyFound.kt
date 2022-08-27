package com.jtmnetwork.play.core.domain.exception.currency

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FOUND, reason = "Currency already found with that name.")
class CurrencyFound: RuntimeException()