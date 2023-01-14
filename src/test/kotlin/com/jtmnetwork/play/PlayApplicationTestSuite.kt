package com.jtmnetwork.play

import com.jtmnetwork.play.core.usecase.repository.economy.CurrencyIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.economy.ExchangeRateIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.economy.TransactionIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.economy.WalletIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.experience.XPProfileRepositoryIntegrationTest
import com.jtmnetwork.play.data.service.economy.CurrencyServiceUnitTest
import com.jtmnetwork.play.data.service.economy.RateServiceUnitTest
import com.jtmnetwork.play.data.service.economy.TransactionServiceUnitTest
import com.jtmnetwork.play.data.service.economy.WalletServiceUnitTest
import com.jtmnetwork.play.entrypoint.controller.economy.CurrencyControllerUnitTest
import com.jtmnetwork.play.entrypoint.controller.economy.RateControllerUnitTest
import com.jtmnetwork.play.entrypoint.controller.economy.TransactionControllerUnitTest
import com.jtmnetwork.play.entrypoint.controller.economy.WalletControllerUnitTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(value = [
    CurrencyIntegrationTest::class,
    ExchangeRateIntegrationTest::class,
    TransactionIntegrationTest::class,
    WalletIntegrationTest::class,

    CurrencyServiceUnitTest::class,
    TransactionServiceUnitTest::class,
    WalletServiceUnitTest::class,
    RateServiceUnitTest::class,

    CurrencyControllerUnitTest::class,
    TransactionControllerUnitTest::class,
    WalletControllerUnitTest::class,
    RateControllerUnitTest::class,

    XPProfileRepositoryIntegrationTest::class
])
class PlayApplicationTestSuite