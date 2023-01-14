package com.jtmnetwork.play.data.service.economy

import com.jtmnetwork.play.core.domain.exception.wallet.WalletFound
import com.jtmnetwork.play.core.domain.exception.wallet.WalletNotFound
import com.jtmnetwork.play.core.usecase.repository.economy.WalletRepository
import com.jtmnetwork.play.core.util.TestUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class WalletServiceUnitTest {

    private val walletRepository: WalletRepository = mock()
    private val walletService = WalletService(walletRepository)

    private val id = UUID.randomUUID().toString()
    private val created = TestUtil.createWallet(id)

    @Test
    fun insertWallet_shouldThrowFound() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.just(created))

        val returned = walletService.insertWallet(created)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .expectError(WalletFound::class.java)
            .verify()
    }

    @Test
    fun insertWallet_shouldReturnWallet() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.empty())
        `when`(walletRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = walletService.insertWallet(created)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun updateWallet_shouldThrowNotFound() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.empty())

        val returned = walletService.updateWallet(created)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .expectError(WalletNotFound::class.java)
            .verify()
    }

    @Test
    fun updateWallet_shouldReturnWallet() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.just(created))
        `when`(walletRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = walletService.updateWallet(created)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun getWallet_shouldThrowNotFound() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.empty())

        val returned = walletService.getWallet(id)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .expectError(WalletNotFound::class.java)
            .verify()
    }

    @Test
    fun getWallet_shouldReturnWallet() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.just(created))

        val returned = walletService.getWallet(id)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun getWallets() {
        `when`(walletRepository.findAll()).thenReturn(Flux.just(created))

        val returned = walletService.getWallets()

        verify(walletRepository, times(1)).findAll()
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun searchWallets_shouldReturnEmpty_whenSearching() {
        `when`(walletRepository.findByNameStartsWithIgnoreCaseOrderByNameDesc(anyString())).thenReturn(Flux.empty())

        val returned = walletService.searchWallets("test")

        verify(walletRepository, times(1)).findByNameStartsWithIgnoreCaseOrderByNameDesc(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .verifyComplete()
    }

    @Test
    fun searchWallets_shouldReturnWallet_whenSearching() {
        `when`(walletRepository.findByNameStartsWithIgnoreCaseOrderByNameDesc(anyString())).thenReturn(Flux.just(created))

        val returned = walletService.searchWallets("test")

        verify(walletRepository, times(1)).findByNameStartsWithIgnoreCaseOrderByNameDesc(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }

    @Test
    fun removeWallet_shouldThrowNotFound() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.empty())

        val returned = walletService.removeWallet(id)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .expectError(WalletNotFound::class.java)
            .verify()
    }

    @Test
    fun removeWallet_shouldReturnWallet() {
        `when`(walletRepository.findById(anyString())).thenReturn(Mono.just(created))
        `when`(walletRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = walletService.removeWallet(id)

        verify(walletRepository, times(1)).findById(anyString())
        verifyNoMoreInteractions(walletRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertWallet(id, it) }
            .verifyComplete()
    }
}