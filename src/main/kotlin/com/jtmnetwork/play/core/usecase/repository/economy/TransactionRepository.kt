package com.jtmnetwork.play.core.usecase.repository.economy

import com.jtmnetwork.play.core.domain.entity.economy.Transaction
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository: ReactiveMongoRepository<Transaction, UUID>