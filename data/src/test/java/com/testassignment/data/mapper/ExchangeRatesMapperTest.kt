package com.testassignment.data.mapper

import com.testassignment.data.MockTestUtil
import io.mockk.MockKAnnotations
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test

class ExchangeRatesMapperTest {

    private lateinit var sut: ExchangeRatesMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = ExchangeRatesMapper()
    }

    @Test
    fun `map exchange rates entity to exchange rates should return converted exchange rates`() {
        val givenExchangeRates = MockTestUtil.getExchangeRatesData()
        val exchangeRateUiState = sut.mapToEntity(givenExchangeRates)

        MatcherAssert.assertThat(exchangeRateUiState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRateUiState.base, CoreMatchers.`is`("USD"))
        MatcherAssert.assertThat(exchangeRateUiState.timestamp, CoreMatchers.`is`(1672824807975))
        MatcherAssert.assertThat(exchangeRateUiState.rates?.size, CoreMatchers.`is`(givenExchangeRates.rates?.currencies?.size))
    }


    @After
    fun tearDown() {
    }
}