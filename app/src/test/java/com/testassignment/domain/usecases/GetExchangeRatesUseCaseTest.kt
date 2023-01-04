package com.testassignment.domain.usecases

import com.testassignment.core.network.Resource
import com.testassignment.core.responses.exchane_rates.ExchangeRatesEntity
import com.testassignment.domain.MockTestUtil
import com.testassignment.domain.repository.CurrencyConverterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetExchangeRatesUseCaseTest {

    private lateinit var sut: GetExchangeRatesUseCase

    @MockK
    lateinit var currencyConverterRepository: CurrencyConverterRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test when getExchangeRateUseCase is invoked, exchangeRatesData is fetched`(){
        runBlocking {
            val givenExchangeRates = MockTestUtil.createExchangeRatesEntity()

            coEvery {
                currencyConverterRepository.getExchangeRates()
            }.returns(flowOf(Resource.success(givenExchangeRates)))

            sut = GetExchangeRatesUseCase(currencyConverterRepository)

            val exchangeRates = sut.invoke()
            MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())

            val exchangeRateDataState = exchangeRates.first()
            MatcherAssert.assertThat(exchangeRateDataState, CoreMatchers.instanceOf(Resource::class.java))
            MatcherAssert.assertThat(exchangeRateDataState.data, CoreMatchers.`is`(givenExchangeRates))
            MatcherAssert.assertThat(exchangeRateDataState.status, CoreMatchers.`is`(Resource.Status.SUCCESS))
            MatcherAssert.assertThat(exchangeRateDataState.data?.rates?.size, CoreMatchers.`is`(givenExchangeRates.rates?.size))
        }
    }

    @Test
    fun `test when getExchangeRateUseCase is invoked, error is returned exchangeRatesData is fetched`(){
        runBlocking {
            val givenErrorMessage = MockTestUtil.getErrorMessage()

            coEvery {
                currencyConverterRepository.getExchangeRates()
            }.returns(flowOf(Resource.error(null, givenErrorMessage)))

            sut = GetExchangeRatesUseCase(currencyConverterRepository)

            val exchangeRates = sut.invoke()
            MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())

            val exchangeRateDataState = exchangeRates.first()
            MatcherAssert.assertThat(exchangeRateDataState, CoreMatchers.instanceOf(Resource::class.java))
            MatcherAssert.assertThat(exchangeRateDataState.status, CoreMatchers.`is`(Resource.Status.ERROR))
            MatcherAssert.assertThat(exchangeRateDataState.message, CoreMatchers.`is`(givenErrorMessage))
        }
    }

    @After
    fun tearDown() {
    }
}