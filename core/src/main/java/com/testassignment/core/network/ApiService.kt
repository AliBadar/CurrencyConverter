package com.testassignment.core.network

import com.testassignment.core.responses.exchane_rates.ExchangeRates
import com.testassignment.core.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(RestConfig.GET_EXCHANGE_RATES)
    suspend fun getExchangeRates(
        @Query("app_id") appid: String = Constants.APP_ID
    ): Response<ExchangeRates>
}