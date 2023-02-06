package com.testassignment.currencyconverter.feature.currency.presentation.ui.currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.testassignment.core.network.Resource
import com.testassignment.core.utils.MyPreference
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

    @MockK
    lateinit var myPreference: MyPreference

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when currencyViewModel is initialized, exchangeRatesData is fetched`(){
        runBlocking {
            val givenExchangeRates = MockTestUtil.createExchangeRatesEntity()

            coEvery {
                myPreference.getBaseCurrencies()
            }returns(MockTestUtil.getBaseCurrenciesList())

            coEvery {
                getExchangeRatesUseCase()
            }.returns(flowOf(Resource.success(givenExchangeRates)))

            sut = CurrencyViewModel(getExchangeRatesUseCase, myPreference)

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

            coEvery {
                myPreference.getBaseCurrencies()
            }returns(MockTestUtil.getBaseCurrenciesList())

            sut = CurrencyViewModel(getExchangeRatesUseCase, myPreference)

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