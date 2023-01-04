package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.testassignment.core.network.Resource
import com.testassignment.currencyconverter.feature.currency.MainCoroutinesRule
import com.testassignment.currencyconverter.feature.currency.MockTestUtil
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.Content
import com.testassignment.currencyconverter.feature.currency.presentation.ui.currency.uistates.ErrorState
import com.testassignment.domain.usecases.GetExchangeRatesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CurrencyViewModelTest {

    private lateinit var sut: CurrencyViewModel

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getExchangeRatesUseCase: GetExchangeRatesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when currencyViewModel is initialized, exchangeRatesData is fetched`(){
        runBlocking {
            val givenExchangeRates = MockTestUtil.createExchangeRatesEntity()

            coEvery {
                getExchangeRatesUseCase()
            }.returns(flowOf(Resource.success(givenExchangeRates)))

            sut = CurrencyViewModel(getExchangeRatesUseCase)

            sut.exchangeRatesData.test {
                assertEquals(Content(givenExchangeRates), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) {
                getExchangeRatesUseCase()
            }

        }
    }

    @Test
    fun `test when currencyViewModel is initialized, error is returned when exchangeRatesData is fetched`(){
        runBlocking {
            val givenErrorMessage = MockTestUtil.getErrorMessage()

            coEvery {
                getExchangeRatesUseCase()
            }.returns(flowOf(Resource.error(null, givenErrorMessage)))

            sut = CurrencyViewModel(getExchangeRatesUseCase)

            sut.exchangeRatesData.test {
                assertEquals(ErrorState(givenErrorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) {
                getExchangeRatesUseCase()
            }
        }
    }

    @After
    fun tearDown() {
    }
}