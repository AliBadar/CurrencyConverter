package com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.testassignment.currencyconverter.feature.currency.MainCoroutinesRule
import com.testassignment.currencyconverter.feature.currency.MockTestUtil
import com.testassignment.currencyconverter.feature.currency.presentation.ui.select_currency.uistates.Content
import com.testassignment.domain.usecases.SelectCurrencyUseCase
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

class SelectCurrencyViewModelTest {

    private lateinit var sut: SelectCurrencyViewModel

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var selectCurrencyUseCase: SelectCurrencyUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when currencyViewModel is initialized, exchangeRatesData is fetched`(){
        runBlocking {
//            List<Currency>
            val givenCurrencyList = MockTestUtil.getCurrencyList()

            coEvery {
                selectCurrencyUseCase()
            }.returns(flowOf(givenCurrencyList))

            sut = SelectCurrencyViewModel(selectCurrencyUseCase)

            sut.selectCurrencyData.test {
                assertEquals(Content(givenCurrencyList), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) {
                selectCurrencyUseCase()
            }

        }
    }

    @After
    fun tearDown() {
    }
}