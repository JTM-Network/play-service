package com.jtmnetwork.play

import com.jtmnetwork.play.core.usecase.repository.CurrencyRepositoryIntegrationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(value = [
    CurrencyRepositoryIntegrationTest::class
])
class PlayApplicationTestSuite