package com.jtmnetwork.play.core.util

import com.jtmnetwork.play.core.domain.entity.Currency
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
    }
}