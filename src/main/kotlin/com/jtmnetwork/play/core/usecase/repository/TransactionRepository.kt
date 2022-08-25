package com.jtmnetwork.play.core.usecase.repository

import com.jtmnetwork.play.core.domain.entity.Transaction
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository: ReactiveMongoRepository<Transaction, UUID>