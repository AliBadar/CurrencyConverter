package com.testassignment.core.network

import com.testassignment.core.MainCoroutinesRule
import com.testassignment.core.network.api.ApiAbstract
import com.testassignment.core.responses.exchane_rates.ExchangeRates
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class ApiServiceTest: ApiAbstract<ApiService>() {

    private lateinit var apiService: ApiService

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(ApiService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `test load latest currencies rates returns exchangeRatesData` () = runBlocking {
        enqueueResponse("/exchange_rates_response.json")

        // Invoke
        val response = apiService.getExchangeRates()
        val responseBody = requireNotNull((response as Response<ExchangeRates>).body())
        mockWebServer.takeRequest()

        // Then
        MatcherAssert.assertThat(responseBody.base, CoreMatchers.`is`("USD"))
        MatcherAssert.assertThat(responseBody.timestamp, CoreMatchers.`is`(1672822800))
        MatcherAssert.assertThat(responseBody.rates?.AED, CoreMatchers.`is`(3.67282))
        MatcherAssert.assertThat(responseBody.rates?.AFN, CoreMatchers.`is`(88.874169))
    }

    @After
    fun tearDown() {
    }
}