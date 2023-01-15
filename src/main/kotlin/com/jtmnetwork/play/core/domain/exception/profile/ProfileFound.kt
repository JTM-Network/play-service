package com.jtmnetwork.play.core.domain.exception.profile

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.FOUND, reason = "Profile already exists.")
class ProfileFound: RuntimeException()