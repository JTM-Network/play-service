package com.jtmnetwork.play.core.domain.exception.wallet

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Wallet not found.")
class WalletNotFound: RuntimeException()