package com.jtmnetwork.play

import com.jtmnetwork.play.core.usecase.repository.CurrencyIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.ExchangeRateIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.TransactionIntegrationTest
import com.jtmnetwork.play.core.usecase.repository.WalletIntegrationTest
import com.jtmnetwork.play.data.service.CurrencyServiceUnitTest
import com.jtmnetwork.play.data.service.RateServiceUnitTest
import com.jtmnetwork.play.data.service.TransactionServiceUnitTest
import com.jtmnetwork.play.data.service.WalletServiceUnitTest
import com.jtmnetwork.play.entrypoint.controller.CurrencyControllerUnitTest
import com.jtmnetwork.play.entrypoint.controller.RateControllerUnitTest
import com.jtmnetwork.play.entrypoint.controller.TransactionControllerUnitTest
import com.jtmnetwork.play.entrypoint.controller.WalletControllerUnitTest
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
])
class PlayApplicationTestSuite