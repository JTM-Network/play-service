package com.jtmnetwork.play.core.domain.exception.currency

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Currency not found.")
class CurrencyNotFound: RuntimeException()