package com.jtmnetwork.play.core.domain.exception.wallet

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FOUND, reason = "Wallet already found for that player.")
class WalletFound: RuntimeException()