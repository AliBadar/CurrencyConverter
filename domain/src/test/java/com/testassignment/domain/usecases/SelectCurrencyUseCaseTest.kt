package com.testassignment.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testassignment.core.network.Resource
import com.testassignment.domain.MainCoroutinesRule
import com.testassignment.domain.MockTestUtil
import com.testassignment.domain.repository.SelectCurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SelectCurrencyUseCaseTest {

    private lateinit var sut: SelectCurrencyUseCase

    @MockK
    lateinit var selectCurrencyRepository: SelectCurrencyRepository

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when getCurrenciesUseCase is invoked, currenciesList is fetched`(){
        runBlocking {
            val givenCurrenciesList = MockTestUtil.getCurrencyList()

            coEvery {
                selectCurrencyRepository.selectCurrency()
            }.returns(flowOf(givenCurrenciesList))

            sut = SelectCurrencyUseCase(selectCurrencyRepository)

            val exchangeRates = sut.invoke()
            MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())

            val exchangeRateDataState = exchangeRates.first()
            MatcherAssert.assertThat(exchangeRateDataState.size, CoreMatchers.`is`(givenCurrenciesList.size))
            MatcherAssert.assertThat(exchangeRateDataState[0].code, CoreMatchers.`is`(givenCurrenciesList[0].code))
        }
    }

    @After
    fun tearDown() {
    }
}