package com.jtmnetwork.play.core.util

import com.jtmnetwork.play.core.domain.entity.economy.Currency
import com.jtmnetwork.play.core.domain.entity.economy.ExchangeRate
import com.jtmnetwork.play.core.domain.entity.economy.Transaction
import com.jtmnetwork.play.core.domain.entity.economy.Wallet
import com.jtmnetwork.play.core.domain.entity.experience.XPProfile
import com.jtmnetwork.play.core.domain.model.experience.XPBalance
import org.assertj.core.api.Assertions.assertThat
import java.util.UUID

class TestUtil {
    companion object {
        fun createCurrency(id: UUID): Currency {
            return Currency(id = id, name = "pounds", abbreviation = "GBP", symbol = "£")
        }

        fun assertCurrency(id: UUID, assert: Currency) {
            assertThat(assert.id).isEqualTo(id)
            assertThat(assert.name).isEqualTo("pounds")
            assertThat(assert.abbreviation).isEqualTo("GBP")
            assertThat(assert.symbol).isEqualTo("£")
        }

        fun createWallet(id: String): Wallet {
            return Wallet(id = id, name = "Joe")
        }

        fun assertWallet(id: String, assert: Wallet) {
            assertThat(assert.id).isEqualTo(id)
            assertThat(assert.name).isEqualTo("Joe")
        }

        fun createTransaction(id: UUID): Transaction {
            return Transaction(id = id)
        }

        fun createXPProfile(id: UUID): XPProfile {
            val balances: MutableMap<UUID, XPBalance> = HashMap()
            balances[UUID.randomUUID()] = XPBalance(0, 0)
            return XPProfile(id, "Joe", balances, System.currentTimeMillis())
        }

        fun assertTransaction(id: UUID, assert: Transaction) {
            assertThat(assert.id).isEqualTo(id)
        }

        fun createExchangeRate(id: UUID): ExchangeRate {
            return ExchangeRate(id = id)
        }

        fun assertExchangeRate(id: UUID, assert: ExchangeRate) {
            assertThat(assert.id).isEqualTo(id)
        }

        fun assertXPProfile(id: UUID, assert: XPProfile) {
            assertThat(assert.id).isEqualTo(id)
            assertThat(assert.name).isEqualTo("Joe")
        }
    }
}